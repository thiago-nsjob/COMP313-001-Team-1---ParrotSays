using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Threading.Tasks;

using Amazon.Lambda.Core;
using Amazon.Lambda.SQSEvents;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using TwitterAnalyzer.Infra.Api;
using TwitterAnalyzer.Infra.Comprehend;
using TwitterAnalyzer.Service;

// Assembly attribute to enable the Lambda function's JSON input to be converted into a .NET class.
[assembly: LambdaSerializer(typeof(Amazon.Lambda.Serialization.Json.JsonSerializer))]
namespace TwitterAnalyzer.Bootstrap
{
    public class Analyzer
    {
        IConfiguration _configuration;
        IServiceProvider _services;

        public Analyzer()
        {
            Amazon.XRay.Recorder.Handlers.AwsSdk.AWSSDKHandler.RegisterXRayForAllServices();

            _configuration = new ConfigurationBuilder()
                .SetBasePath(Directory.GetCurrentDirectory())
                .AddJsonFile("appsettings.json", optional: true, reloadOnChange: true)
                .AddEnvironmentVariables("AWS_")
                .Build();

            _services = ConfigureServices(new ServiceCollection())
                            .BuildServiceProvider();

        }

        private IServiceCollection ConfigureServices(IServiceCollection serviceCollection)
        {
            serviceCollection.AddSingleton<IComprehendService, ComprehendClient>(s => 
                new ComprehendClient(
                    _configuration["AWS_COMPREHEND_ENDPOINT"]
                    ));

            serviceCollection.AddTransient<ITwitterAnalyzer, TwitterAnalyzerService>();
            return serviceCollection;
        }

        public async Task<string> Handler(SQSEvent evnt, ILambdaContext context)
        {
            Console.WriteLine("Analyzer started");

            await _services.GetService<ITwitterAnalyzer>().ProcessPosts(evnt);

            Console.WriteLine("Analyzer finished");

            return "Posts processed";

        }

    }
}
