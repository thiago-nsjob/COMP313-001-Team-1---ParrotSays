using System;
using System.Collections.Generic;
using System.Text;
using Newtonsoft.Json;

namespace TwitterScraper.Infra.Api
{
    public interface IResult
    {
        string ToJson();
    }
}
