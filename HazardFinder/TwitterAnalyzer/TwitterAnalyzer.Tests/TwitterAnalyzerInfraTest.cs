using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;
using TwitterAnalyzer.Infra.Api;
using TwitterAnalyzer.Infra.Comprehend;
using TwitterAnalyzer.Infra.ParrotSays;
using Xunit;

namespace TwitterAnalyzer.Tests
{
   public class TwitterAnalyzerInfraTest
   {

        [Fact]
        public void ComprehendClient_Constructor()
        {
            IComprehendService client =
                   new ComprehendClient("https://www.centennialcollege.ca/studenthub/");
            Assert.NotNull(client);
        }

        [Fact]
        public async Task Classify_Method()
        {
            IComprehendService client =
                  new ComprehendClient("https://www.centennialcollege.ca/studenthub/");

            var list = await client.Classify("word");

            Assert.NotNull(list);

        }

        [Fact]
        public async Task DetectSentiment_Method()
        {
            IComprehendService client =
                  new ComprehendClient("https://www.centennialcollege.ca/studenthub/");

            var l = await client.DetectSentiment("word");

            Assert.NotNull(l);

        }

        [Fact]
        public void  ParrotSaysClient_Constructor()
        {
            ParrotSaysClient client =
                   new ParrotSaysClient("https://www.centennialcollege.ca/studenthub/");
            Assert.NotNull(client);
        }
    }
}
