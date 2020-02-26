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
        string _accountId;
        string _queueUrl;

        public SQSClient(string serviceLink, string queueName, string accountId)
        {
            AmazonSQSConfig amazonSQSConfig = new AmazonSQSConfig();
            amazonSQSConfig.ServiceURL = serviceLink;

            _accountId = accountId;
            _queueName = queueName;
            _client = new AmazonSQSClient(amazonSQSConfig);

            _queueUrl = _client.GetQueueUrlAsync(new GetQueueUrlRequest()
            {
                QueueName = _queueName,
                QueueOwnerAWSAccountId = _accountId
            }).Result.QueueUrl;

        }


        public async Task<bool> SendBatchMessage(ICollection<IResult> lstMessages)
        {
            try
            {
                var slice = 10;
                var result = true;
                for (int i = 0; i < lstMessages.Count(); i =i + slice)
                {

                    var response = await _client.SendMessageBatchAsync(
                        new SendMessageBatchRequest(
                            _queueUrl,
                            lstMessages
                            .Skip(i)
                            .Take(slice)
                            .Select(item => new SendMessageBatchRequestEntry(Guid.NewGuid().ToString(), item.ToJson()))
                            .ToList()
                        ));
                    
                    result = response.Failed.Count() == 0;
                }
                return result;
            }
            catch (Exception ex)
            {
                Console.WriteLine("Fail to send messages.");
                Console.WriteLine(ex.Message);
                throw ex;
            }
        }

    }
}
