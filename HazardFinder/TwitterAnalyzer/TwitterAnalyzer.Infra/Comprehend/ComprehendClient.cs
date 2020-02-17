using Amazon.Comprehend;
using Amazon.Comprehend.Model;
using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;
using TwitterAnalyzer.Infra.Api;

namespace TwitterAnalyzer.Infra.Comprehend
{
    public class ComprehendClient : IComprehendService
    {
        AmazonComprehendClient _client;
        string _endpoint;

        public ComprehendClient(string endpoint)
        {
            AmazonComprehendConfig config = new AmazonComprehendConfig();
            _client = new AmazonComprehendClient(config);
            _endpoint = endpoint;
        }

        public async Task<List<DocumentClass>> Classify(string text)
        {
            try
            {
                var response = await _client.ClassifyDocumentAsync(
                    new ClassifyDocumentRequest()
                    {
                        EndpointArn = _endpoint,
                        Text = text
                    });
                return response.Classes;
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
                throw ex;
            }
        }

        public async Task<DetectSentimentResponse> DetectSentiment(string text)
        {
            try
            {
                var response = await _client.DetectSentimentAsync(
                    new DetectSentimentRequest()
                    {
                        LanguageCode = LanguageCode.En,
                        Text = text
                    });
                return response;
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
                throw ex;
            }
        }
    }
}
