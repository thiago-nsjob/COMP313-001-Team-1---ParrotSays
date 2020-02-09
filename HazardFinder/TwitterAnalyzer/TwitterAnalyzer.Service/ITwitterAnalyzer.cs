using Amazon.Lambda.SQSEvents;
using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;

namespace TwitterAnalyzer.Service
{
    public interface ITwitterAnalyzer
    {
        Task ProcessPosts(SQSEvent evnt);
    }
}
