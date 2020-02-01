using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Amazon.Lambda.Core;
using Microsoft.Extensions.DependencyInjection.Extensions;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Configuration;
using TwitterScraper.Infra.Api;
using TwitterScraper.Infra.Twitter;
using TwitterScraper.Infra.SQS;
using TwitterScraper.Service;
using System.IO;

// Assembly attribute to enable the Lambda function's JSON input to be converted into a .NET class.
[assembly: LambdaSerializer(typeof(Amazon.Lambda.Serialization.Json.JsonSerializer))]
namespace TwitterScraper.Bootstrap
{
    public class Scraper
    {
        IConfiguration _configuration;
        IServiceProvider _services;

        public Scraper()
        {
            Amazon.XRay.Recorder.Handlers.AwsSdk.AWSSDKHandler.RegisterXRayForAllServices();

            _configuration = new ConfigurationBuilder()
                .SetBasePath(Directory.GetCurrentDirectory())
                .AddJsonFile("appsettings.json", optional: true, reloadOnChange: true)
                .AddEnvironmentVariables("AWS_TWITTER_")
                .Build();

            _services = ConfigureServices(new ServiceCollection())
                            .BuildServiceProvider();
        }
        private IServiceCollection ConfigureServices(IServiceCollection serviceCollection)
        {
            serviceCollection.AddSingleton<IScraperService, TwitterClient>(s => new TwitterClient(
                _configuration["AWS_TWITTER_API_KEY"],
                _configuration["AWS_TWITTER_API_SECRET_KEY"],
                _configuration["AWS_TWITTER_API_TOKEN"],
                _configuration["AWS_TWITTER_API_SECRET"]
                ));

            serviceCollection.AddSingleton<ISQSService, SQSClient>(s => new SQSClient(
                    _configuration["AWS_TWITTER_SQS_SERVICE"].ToString(),
                    _configuration["AWS_TWITTER_SQS_QUEUENAME"].ToString(),
                    _configuration["AWS_TWITTER_ACCOUNT_ID"].ToString()
                )
            );
            serviceCollection.AddTransient<ITwitterScraper, TwitterScraperService>();
            return serviceCollection;
        }

        public async Task<string> Handler(ILambdaContext context)
        {
            Console.WriteLine("Scraper started");

            await _services.GetService<ITwitterScraper>().ProcessPosts("centennialcollege");

            Console.WriteLine("Scraper finished");

            return "Posts processed";
        }
    }
}
