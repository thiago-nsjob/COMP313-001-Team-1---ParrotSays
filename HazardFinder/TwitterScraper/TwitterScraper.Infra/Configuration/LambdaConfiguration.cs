using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.Primitives;
using System;
using System.Collections.Generic;
using System.IO;
using System.Text;

namespace TwitterScraper.Infra.Configuration
{
    public class LambdaConfiguration : ILambdaConfiguration
    {
        public LambdaConfiguration()
        {
        }
        public  IConfiguration Configuration => new ConfigurationBuilder()
                   .SetBasePath(Directory.GetCurrentDirectory())
                   .AddJsonFile("appsettings.json", optional: true, reloadOnChange: true)
                   .Build();
    }
}
