using System;
using System.Collections.Generic;
using System.Threading.Tasks;

using Xunit;
using TwitterScraper.Infra.Api;
using TwitterScraper.Infra.SQS;
using TwitterScraper.Infra.Twitter;
namespace TwitterScraper.Tests
{
    public class TwitterScraperInfraTest
    {

        [Fact]
        public async Task TwitterClient_Constructor()
        {
            TwitterClient client =
                   new TwitterClient("dakmNn0r3dhbMaUc4lvo46ErA",
               "RUPlnEIgGrgZ0NnK4diylDorm23zYBfERaIBNkNmvu9l7ZLzb8",
               "1222242177314631681-MAtzVci7wbHqyw1G4vz5YEfAGRfIby",
               "qDP1TUtFE0D0gGfc1gmXu9e7rILxZOhKjHWMhFSBbPw6H");

            Assert.NotNull(client);
        }

        [Fact]
        public async Task TwitterClient()
        {
            TwitterClient client =
                new TwitterClient("dakmNn0r3dhbMaUc4lvo46ErA",
            "RUPlnEIgGrgZ0NnK4diylDorm23zYBfERaIBNkNmvu9l7ZLzb8",
            "1222242177314631681-MAtzVci7wbHqyw1G4vz5YEfAGRfIby",
            "qDP1TUtFE0D0gGfc1gmXu9e7rILxZOhKjHWMhFSBbPw6H");

            var result = await client.GetData("centennialcollege");

            Assert.NotEmpty(result);
        }

        [Fact]
        public async Task SQSClient_Constructor()
        {
            ISQSService service = new SQSClient("https://sqs.ca-central-1.amazonaws.com", "IncomeTwitterPosts", "113508044065");

            Assert.NotNull(service);
     
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

       
    }
}
