using System;
using System.Threading.Tasks;
using TwitterScraper.Infra;
using TwitterScraper.Infra.Api;
using TwitterScraper.Infra.SQS;

namespace TwitterScraper.Service
{
    public class TwitterScraperService : ITwitterScraper
    {
        IScraperService _scraper;
        ISQSService _sqsClient;

        public TwitterScraperService(IScraperService scraper, ISQSService sqsClient)
        {
            _scraper = scraper;
            _sqsClient = sqsClient;
        }
        public async Task ProcessPosts(string hastags) => await _sqsClient.SendBatchMessage(await _scraper.GetData(hastags));


    }
}
