using Microsoft.Extensions.Configuration;
using System;

namespace TwitterScraper.Infra.Configuration
{
    public interface ILambdaConfiguration
    {
        IConfiguration Configuration { get; }

    }
}
