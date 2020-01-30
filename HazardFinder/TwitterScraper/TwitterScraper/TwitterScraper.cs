using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Amazon.Lambda.Core;
using Amazon.Lambda.CloudWatchEvents;

// Assembly attribute to enable the Lambda function's JSON input to be converted into a .NET class.
[assembly: LambdaSerializer(typeof(Amazon.Lambda.Serialization.Json.JsonSerializer))]

namespace TwitterScraper.Bootstrap
{
    public class TwitterScraper
    {

        public TwitterScraper()
        {
            Amazon.XRay.Recorder.Handlers.AwsSdk.AWSSDKHandler.RegisterXRayForAllServices();
        }

       
        public async Task<string> TwitterHandler(CloudWatchEvent<object> @event, ILambdaContext context)
        {
            Console.WriteLine("Lambda fired");
            return @event.Id;
        }
    }
}
