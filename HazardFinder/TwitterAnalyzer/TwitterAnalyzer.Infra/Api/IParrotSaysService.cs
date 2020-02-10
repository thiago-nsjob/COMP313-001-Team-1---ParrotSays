using System.Net.Http;
using System.Threading.Tasks;

namespace TwitterAnalyzer.Infra.ParrotSays
{
    public interface IParrotSaysService
    {
        Task<HttpResponseMessage> SendMessage(Post post);
    }
}