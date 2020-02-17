using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

using Xunit;
using Amazon.Lambda;
using Amazon.Lambda.Core;
using Amazon.Lambda.TestUtilities;

using Amazon;

using TwitterAnalyzer;
using Amazon.Lambda.SQSEvents;
using TwitterAnalyzer.Bootstrap;

namespace TwitterAnalyzer.Tests
{
    public class TwitterAnalyzerTest
    {

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
