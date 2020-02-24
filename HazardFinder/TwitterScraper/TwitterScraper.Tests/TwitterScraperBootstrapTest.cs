using System;
using System.Collections.Generic;
using System.Text;

namespace TwitterScraper.Tests
{
    class TwitterScraperBootstrapTest
    {
        [Fact]
        public async Task Handle()
        {
            var scraper = new TwitterScraper.Bootstrap.Scraper();
            await scraper.Handler(null);

        }
    }
}
