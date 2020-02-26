using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;
using TwitterAnalyzer.Infra.ParrotSays;
using Xunit;

namespace TwitterAnalyzer.Tests
{
   public class TwitterAnalyzerInfraTest
    {
        [Fact]
        public async Task ParrotSaysClient_Constructor()
        {
            ParrotSaysClient client =
                   new ParrotSaysClient("https://www.centennialcollege.ca/studenthub/");
            Assert.NotNull(client);
        }

    }
}
