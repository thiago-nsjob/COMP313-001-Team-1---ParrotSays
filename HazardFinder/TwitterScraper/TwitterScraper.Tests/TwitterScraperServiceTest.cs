using System;
using System.Collections.Generic;
using System.Text;
using TwitterScraper.Infra.Api;
using TwitterScraper.Infra.SQS;
using TwitterScraper.Infra.Twitter;
using TwitterScraper.Service;
using Xunit;

namespace TwitterScraper.Tests
{
    public class TwitterScraperServiceTest
    {
      
        [Fact]
        public void TwitterScraperService_Constructor()
        {
            IScraperService _scraper = 
               new TwitterClient("dakmNn0r3dhbMaUc4lvo46ErA",
           "RUPlnEIgGrgZ0NnK4diylDorm23zYBfERaIBNkNmvu9l7ZLzb8",
           "1222242177314631681-MAtzVci7wbHqyw1G4vz5YEfAGRfIby",
           "qDP1TUtFE0D0gGfc1gmXu9e7rILxZOhKjHWMhFSBbPw6H");

            ISQSService _sqsClient = new SQSClient("https://sqs.ca-central-1.amazonaws.com", "IncomeTwitterPosts", "113508044065");

            ITwitterScraper client =
                   new TwitterScraperService(_scraper, _sqsClient);

            Assert.NotNull(client);
        }
    }
}
