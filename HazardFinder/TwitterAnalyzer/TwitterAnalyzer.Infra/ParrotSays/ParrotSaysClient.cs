using System;
using System.Collections.Generic;
using System.Net.Http;
using System.Net.Mime;
using System.Text;
using System.Threading.Tasks;

namespace TwitterAnalyzer.Infra.ParrotSays
{
    public class ParrotSaysClient : IParrotSaysService
    {
        HttpClient _client;
        string _url;

        public ParrotSaysClient(string url)
        {
            _url = url;
            _client = new HttpClient();
        }
        public async Task<HttpResponseMessage> SendMessage(Post post)
        {
            try
            {
                var content = new StringContent(post.ToJson(),
                                        Encoding.UTF8,
                                        MediaTypeNames.Application.Json);

                var response = await _client.PostAsync(_url, content);
                return response.EnsureSuccessStatusCode();

            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
                throw ex;
            }
        }
    }
}
