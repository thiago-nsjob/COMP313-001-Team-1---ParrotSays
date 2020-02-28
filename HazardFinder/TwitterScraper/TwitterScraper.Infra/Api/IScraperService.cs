using System;
using System.Collections;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;

namespace TwitterScraper.Infra.Api
{
    public interface IScraperService
    {
         Task<ICollection<IResult>> GetData(string hashtags, int range); 
    }
}
