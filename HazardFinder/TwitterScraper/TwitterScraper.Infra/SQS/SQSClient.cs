using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Amazon.SQS;
using Amazon.SQS.Model;
using TwitterScraper.Infra.Api;

namespace TwitterScraper.Infra.SQS
{
    public class SQSClient : ISQSService
    {

        AmazonSQSClient _client;
        string _queueName;
        public SQSClient(string serviceLink, string queueName)
        {
            AmazonSQSConfig amazonSQSConfig = new AmazonSQSConfig();
            amazonSQSConfig.ServiceURL = serviceLink;

            _queueName = queueName;
            _client = new AmazonSQSClient(amazonSQSConfig);

        }


        public async Task<bool> SendBatchMessage(ICollection<IResult> lstMessages)
        {
            var result = await _client.SendMessageBatchAsync(
                new SendMessageBatchRequest(
                    _queueName,
                    lstMessages
                    .Select(item => new SendMessageBatchRequestEntry(Guid.NewGuid().ToString(), item.ToJson()))
                    .ToList()
                ));

            return result.Failed.Count() == 0;
        }

    }
}
