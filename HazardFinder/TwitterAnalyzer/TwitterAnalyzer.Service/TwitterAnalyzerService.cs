using Amazon.Lambda.SQSEvents;
using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;

namespace TwitterAnalyzer.Service
{
    public class TwitterAnalyzerService : ITwitterAnalyzer
    {
        public async Task ProcessPosts(SQSEvent evnt)
        {
            Console.WriteLine(evnt);
            await Task.CompletedTask;
        }
    }
}
