using Microsoft.Extensions.DependencyInjection;
using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;
using TwitterScraper.Bootstrap;
using Xunit;

namespace TwitterScraper.Tests
{
    public class TwitterScraperBootstrapTest
    {
        
        [Fact]
        public void Scraper_Constructor()
        {
            Scraper client =
                   new Scraper();

            Assert.NotNull(client);
        }

        [Fact]
        public async Task Handle()
        {
            var scraper = new TwitterScraper.Bootstrap.Scraper();
            await scraper.Handler(null);
        }
    }
}
