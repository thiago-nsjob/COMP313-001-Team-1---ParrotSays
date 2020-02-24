using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;
using Xunit;

namespace TwitterScraper.Tests
{
    public class TwitterScraperBootstrapTest
    {
        [Fact]
        public async Task Handle()
        {
            var scraper = new TwitterScraper.Bootstrap.Scraper();
            await scraper.Handler(null);

        }
    }
}
