using Amazon.Comprehend.Model;
using Amazon.Lambda.SQSEvents;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;
using TwitterAnalyzer.Infra.Api;
using TwitterAnalyzer.Infra.ParrotSays;

namespace TwitterAnalyzer.Service
{
    public class TwitterAnalyzerService : ITwitterAnalyzer
    {
        IComprehendService _comprehendClient;

        public TwitterAnalyzerService(IComprehendService comprehendClient)
        {
            _comprehendClient = comprehendClient;
        }

        public async Task ProcessPosts(SQSEvent evnt)
        {
            Post post = Post.FromJson(evnt.Records[0].Body);
            
            var classes = await _comprehendClient.Classify(post.Text);
            post.MLAnalyse.Add("classification", classes);

            var sentiment = await _comprehendClient.DetectSentiment(post.Text);
            post.MLAnalyse.Add("sentiment", sentiment);


        }
    }
}
