using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;
using System.Linq;
using System.Net.Http;
using TwitterScraper.Infra;
using Newtonsoft.Json;
using System.IO;
using TwitterScraper.Infra.Api;
using Tweetinvi;
using Tweetinvi.Models;
using Tweetinvi.Parameters;
using StackExchange.Redis;

namespace TwitterScraper.Infra.Twitter
{
    public class TwitterClient : IScraperService
    {
        private IAuthenticatedUser _user;
        private IDatabase _cache;

        public TwitterClient(string consumerkey, string consumerSecret, string accessToken, string accessTokenSecret, IDatabase cache)
        {
            try
            {
                Auth.SetCredentials(new TwitterCredentials(consumerkey, consumerSecret, accessToken, accessTokenSecret));
                _user = User.GetAuthenticatedUser();
                _cache = cache;
            }
            catch (Exception ex)
            {
                Console.WriteLine("Fail to initialize TwitterClient class.");
                Console.WriteLine(ex.Message);
                throw ex;
            }
        }

        public async Task<ICollection<IResult>> GetData(string hashtags, int range)
        {
            try
            {

                var tweets = Search.SearchTweets(new SearchTweetsParameters(hashtags)
                {
                    Lang = LanguageFilter.English,
                    SearchType = SearchResultType.Recent,
                    MaximumNumberOfResults = 100,
                    Filters = TweetSearchFilters.Hashtags
                });

                var result = await Task.WhenAll(tweets
                   .Select(item => Task.FromResult(new Post()
                   {
                       Id = item.Id.ToString(),
                       CreatedAt = item.CreatedAt,
                       CreatedBy = item.CreatedBy.Description,
                       HashTags = item.Hashtags.Select(tag => tag.Text).ToList(),
                       PostURL = item.Url,
                       Text = item.FullText
                   })).ToArray()
                   );

                result.Where(post =>(DateTime.Now - post.CreatedAt).TotalDays <= range && !_cache.KeyExists(post.Id));



                return result
                    .Where(post => (DateTime.Now - post.CreatedAt).TotalDays <= range && !_cache.KeyExists(post.Id))
                    .ToList<IResult>();
            }
            catch (Exception ex)
            {
                Console.WriteLine("Fail to get Twitter posts");
                Console.WriteLine(ex.Message);

                throw ex;
            }
        }


    }
}
