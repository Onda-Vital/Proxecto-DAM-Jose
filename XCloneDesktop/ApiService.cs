using System;
using System.Collections.Generic;
using System.Net.Http;
using System.Text;
using System.Text.Json;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace XCloneDesktop
{
    public class ApiService
    {
        private readonly HttpClient httpClient;

        public ApiService()
        {
            httpClient = new HttpClient();
            httpClient.BaseAddress = new Uri("http://localhost:8080/xapi/rest/");
        }

        public async Task<User> LoginAsync(string username, string password)
        {
            var datos = new
            {
                username = username,
                password = password
            };

            string json = JsonSerializer.Serialize(datos);
            var content = new StringContent(json, Encoding.UTF8, "application/json");

            HttpResponseMessage response = await httpClient.PostAsync("user/login", content);

            if (!response.IsSuccessStatusCode)
                return null;

            string body = await response.Content.ReadAsStringAsync();

            return JsonSerializer.Deserialize<User>(body,
                new JsonSerializerOptions { PropertyNameCaseInsensitive = true });
        }

        public async Task<bool> RegisterAsync(string username, string email, string password, string displayName)
        {
            var datos = new
            {
                username = username,
                email = email,
                password = password,
                display_name = displayName
            };

            string json = JsonSerializer.Serialize(datos);
          //  MessageBox.Show("JSON rexistro:\n" + json);

            var content = new StringContent(json, Encoding.UTF8, "application/json");
            HttpResponseMessage response = await httpClient.PostAsync("user", content);

            string body = await response.Content.ReadAsStringAsync();
        //    MessageBox.Show("Código: " + (int)response.StatusCode + "\nResposta: " + body);

            return response.IsSuccessStatusCode;
        }

        public async Task<List<Tweet>> GetTweetsAsync()
        {
            HttpResponseMessage response = await httpClient.GetAsync("tweet");

            if (!response.IsSuccessStatusCode)
                return new List<Tweet>();

            string body = await response.Content.ReadAsStringAsync();

            return JsonSerializer.Deserialize<List<Tweet>>(body,
                new JsonSerializerOptions { PropertyNameCaseInsensitive = true }) ?? new List<Tweet>();
        }

        public async Task<bool> CreateTweetAsync(string username, string contentText)
        {
            var datos = new
            {
                username = username,
                handle = "@" + username,
                content = contentText
            };

            string json = JsonSerializer.Serialize(datos);
          //  MessageBox.Show("JSON tweet:\n" + json);

            var content = new StringContent(json, Encoding.UTF8, "application/json");
            HttpResponseMessage response = await httpClient.PostAsync("tweet", content);

            string body = await response.Content.ReadAsStringAsync();
            //MessageBox.Show("Código: " + (int)response.StatusCode + "\nResposta: " + body);

            return response.IsSuccessStatusCode;
        }

        public async Task<bool> DeleteTweetAsync(long id, string username)
        {
            HttpResponseMessage response = await httpClient.DeleteAsync($"tweet/{id}?username={username}");
            return response.IsSuccessStatusCode;
        }

        public async Task<bool> DeleteUserAsync(long id)
        {
            HttpResponseMessage response = await httpClient.DeleteAsync($"user/{id}");
            return response.IsSuccessStatusCode;
        }
    }
}
