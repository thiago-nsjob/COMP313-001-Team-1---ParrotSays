using Amazon.Lambda.SQSEvents;
using Amazon.Lambda.TestUtilities;
using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;
using TwitterAnalyzer.Bootstrap;
using TwitterAnalyzer.Infra.Api;
using TwitterAnalyzer.Infra.Comprehend;
using Xunit;

namespace TwitterAnalyzer.Tests
{
    public class TwitterAnalyzerBootstrapTest
    {
        [Fact]
        public void Analyzer_Constructor()
        {
            Analyzer analyze =
                   new Analyzer();
            Assert.NotNull(analyze);
        }

        [Fact]
        public async Task TestSQSEventLambdaFunction()
        {
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

            var logger = new TestLambdaLogger();
            var context = new TestLambdaContext
            {
                Logger = logger
            };

            var analyzer = new Analyzer();
            await analyzer.Handler(sqsEvent, context);

            Assert.Contains("Processed", logger.Buffer.ToString());
        }

    }
}
