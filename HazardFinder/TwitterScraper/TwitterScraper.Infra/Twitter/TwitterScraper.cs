using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;
using System.Net.Http;

namespace TwitterScraper.Infra.Twitter
{
    public class TwitterScraper
    {
        private HttpClient _client;

        public TwitterScraper() {
            _client = new HttpClient();
        }

        public async Task<List<Post>> GetPosts() {

            var result =_client.GetAsync("");

            return await Task.FromResult(new List<Post>());

        }
    }
}
