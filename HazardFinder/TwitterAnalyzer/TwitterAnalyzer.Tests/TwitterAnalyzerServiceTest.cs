using Amazon.Lambda.SQSEvents;
using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;
using TwitterAnalyzer.Service;
using Xunit;

namespace TwitterAnalyzer.Tests
{
    public class TwitterAnalyzerServiceTest
    {

        [Fact]
        public void TwitterAnalyzerService_Constructor()
        {
            ITwitterAnalyzer analyze =
                   new TwitterAnalyzerService(null, null, null, 0, 0);

            Assert.NotNull(analyze);
            
        }

        [Fact]
        public async Task ProcessPost()
        {
            ITwitterAnalyzer analyze =
                   new TwitterAnalyzerService(null, null, null, 0, 0);
            Assert.NotNull(analyze);


            var sqsEvent = new SQSEvent
            {
                Records = new List<SQSEvent.SQSMessage>
                {
                    new SQSEvent.SQSMessage
                    {
                        Body = "foobar"
                    }
                }
            };

            var ret = await analyze.ProcessPost(sqsEvent);

            Assert.True(ret);

        }

    }
}
