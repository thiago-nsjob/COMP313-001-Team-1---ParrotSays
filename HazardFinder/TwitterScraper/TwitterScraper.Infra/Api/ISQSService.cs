using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;

namespace TwitterScraper.Infra.Api
{
    public interface ISQSService
    {
        Task<bool> SendBatchMessage(ICollection<IResult> lstMessages);
    }
}
