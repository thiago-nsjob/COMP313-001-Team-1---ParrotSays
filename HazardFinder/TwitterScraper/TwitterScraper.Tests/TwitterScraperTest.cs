using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

using Xunit;
using Amazon.Lambda;
using Amazon.Lambda.Core;
using Amazon.Lambda.TestUtilities;
using Amazon.Lambda.S3Events;

using Amazon;
using Amazon.S3;
using Amazon.S3.Model;
using Amazon.S3.Util;

using Scraper;
using TwitterScraper.Infra.Api;
using TwitterScraper.Infra.SQS;
using TwitterScraper.Infra.Twitter;

namespace Scraper.Tests
{
    public class TwitterScraperTest
    {



        [Fact]
        public async Task TwitterClient()
        {
            TwitterScraper.Infra.Twitter.TwitterClient client =
                new TwitterScraper.Infra.Twitter.TwitterClient("dakmNn0r3dhbMaUc4lvo46ErA",
            "RUPlnEIgGrgZ0NnK4diylDorm23zYBfERaIBNkNmvu9l7ZLzb8",
            "1222242177314631681-MAtzVci7wbHqyw1G4vz5YEfAGRfIby",
            "qDP1TUtFE0D0gGfc1gmXu9e7rILxZOhKjHWMhFSBbPw6H");

            var result = await client.GetData("centennialcollege");

            Assert.NotEmpty(result);
        }

        [Fact]
        public async Task SQSClient()
        {
            ICollection<IResult> lst = new List<IResult>() { (IResult)new Post() {
                CreatedAt = DateTime.Now,
                CreatedBy = "me",
                HashTags = new List<string>() { "someone" }  ,
                Id = Guid.NewGuid().ToString(),
                PostURL="",
                Text = "Sometext"
            } };

            ISQSService service = new SQSClient("https://sqs.ca-central-1.amazonaws.com", "IncomeTwitterPosts", "113508044065");

            Assert.True(await service.SendBatchMessage(lst));

        }

        [Fact]
        public async Task Handle()
        {
            var scraper = new TwitterScraper.Bootstrap.Scraper();
            await scraper.Handler(null);
        }
    }
}
