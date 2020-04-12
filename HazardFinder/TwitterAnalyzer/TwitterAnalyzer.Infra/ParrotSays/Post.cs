using Newtonsoft.Json;
using Newtonsoft.Json.Serialization;
using System;
using System.Collections.Generic;
using System.Text;
using TwitterAnalyzer.Infra.Api;

namespace TwitterAnalyzer.Infra.ParrotSays
{
    public class Post : IResult
    {
        public string Id { get; set; }
        public string CreatedBy { get; set; }
        public string Text { get; set; }
        public List<string> HashTags { get; set; }
        public string PostUrl { get; set; }
        public DateTime CreatedAt { get; set; }
        public IDictionary<string, object> MLAnalyse { get; set; }

        public Post()
        {
            MLAnalyse = new Dictionary<string, object>();
        }

        public string ToJson()
        {
            return JsonConvert.SerializeObject(this,
                new JsonSerializerSettings
                {
                    ContractResolver = new CamelCasePropertyNamesContractResolver()
                });
        }

        public static Post FromJson(string json)
        {
            return JsonConvert.DeserializeObject<Post>(json);
        }
    }
}
