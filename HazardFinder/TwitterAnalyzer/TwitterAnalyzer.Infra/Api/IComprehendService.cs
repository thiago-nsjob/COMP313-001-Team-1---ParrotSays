using Amazon.Comprehend.Model;
using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;

namespace TwitterAnalyzer.Infra.Api
{
    public interface IComprehendService
    {
        Task<List<DocumentClass>> Classify(string text);

        Task<DetectSentimentResponse> DetectSentiment(string text);
    }
}
