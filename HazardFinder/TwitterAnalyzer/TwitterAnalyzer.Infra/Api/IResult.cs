using System;
using System.Collections.Generic;
using System.Text;

namespace TwitterAnalyzer.Infra.Api
{
    public interface IResult
    {
        string ToJson();
    }
}
