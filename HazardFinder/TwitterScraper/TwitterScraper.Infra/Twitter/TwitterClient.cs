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


namespace TwitterScraper.Infra.Twitter
{
    public class TwitterClient : IScraperService
    {
        private IAuthenticatedUser _user;
        

        public TwitterClient(string consumerkey, string consumerSecret, string accessToken, string accessTokenSecret )
        {
            try
            {
                Auth.SetCredentials(new TwitterCredentials(consumerkey, consumerSecret, accessToken, accessTokenSecret));
                _user = User.GetAuthenticatedUser();
             
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

                var result = tweets
                    .Where(tweet => (DateTime.Now - tweet.CreatedAt).TotalDays <= range)
                    .Select(tweet =>
                    {
                        return new Post()
                        {
                            Id = tweet.Id.ToString(),
                            CreatedAt = tweet.CreatedAt,
                            CreatedBy = tweet.CreatedBy.Description,
                            HashTags = tweet.Hashtags.Select(tag => tag.Text).ToList(),
                            PostURL = tweet.Url,
                            Text = tweet.FullText
                        };
                    });

                return await Task.FromResult((ICollection<IResult>)result.ToList<IResult>());
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
