using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Text;
using TwitterScraper.Infra.Api;

namespace TwitterScraper.Infra.Twitter
{
    public class Post : IResult
    {
        public Post() { }

        public string Id { get; set; }
        public string CreatedBy { get; set; }
        public string Text { get; set; }
        public List<string> HashTags { get; set; }
        public string PostURL { get; set; }
        public DateTime CreatedAt { get; set; }

        public string ToJson()
        {
            return JsonConvert.SerializeObject(this);
        }
    }
}
