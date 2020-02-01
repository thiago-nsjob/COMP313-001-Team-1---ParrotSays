using System.Threading.Tasks;

namespace TwitterScraper.Service
{
    public interface ITwitterScraper
    {
        Task ProcessPosts(string hastags);
    }
}