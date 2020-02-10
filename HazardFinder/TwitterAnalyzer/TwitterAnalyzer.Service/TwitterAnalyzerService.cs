﻿using Amazon.Comprehend;
using Amazon.Comprehend.Model;
using Amazon.Lambda.SQSEvents;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using TwitterAnalyzer.Infra.Api;
using TwitterAnalyzer.Infra.ParrotSays;

namespace TwitterAnalyzer.Service
{
    public class TwitterAnalyzerService : ITwitterAnalyzer
    {
        IComprehendService _comprehendClient;
        IParrotSaysService _parrotSaysClient;
        float _classifierConfidenceLevel;
        float _sentimentConfidenceLevel;

        public TwitterAnalyzerService(
            IComprehendService comprehendClient,
            IParrotSaysService parrotSaysClient, 
            float classifierConfidenceLevel,
            float sentimentConfidenceLevel)
        {
            _comprehendClient = comprehendClient;
            _parrotSaysClient = parrotSaysClient;
            _classifierConfidenceLevel = classifierConfidenceLevel;
            _sentimentConfidenceLevel = sentimentConfidenceLevel;
        }

        public async Task<bool> ProcessPost(SQSEvent evnt)
        {
            Post post = Post.FromJson(evnt.Records[0].Body);

            var classes = await _comprehendClient.Classify(post.Text);
            post.MLAnalyse.Add("classification", classes);

            var sentiment = await _comprehendClient.DetectSentiment(post.Text);
            post.MLAnalyse.Add("sentiment", sentiment);

            bool result = false;

            if (isHazard(classes, sentiment))
            {
                Console.WriteLine("Hazard detected.");
                var response = await _parrotSaysClient.SendMessage(post);
                result = response.IsSuccessStatusCode;
            }

            return result;
        }

        private bool isHazard(List<DocumentClass> classes, DetectSentimentResponse sentiment)
        {
            bool hazardProbability = classes.Exists(c => c.Score >= _classifierConfidenceLevel);

            bool neutralOrNegativeSentiment =
                !(sentiment.Sentiment.Equals(SentimentType.POSITIVE) &&
                    sentiment.SentimentScore.Positive >= _sentimentConfidenceLevel);

            return hazardProbability && neutralOrNegativeSentiment;
        }
    }
}
