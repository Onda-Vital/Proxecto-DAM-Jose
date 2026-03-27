using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace XCloneDesktop
{
    public class Tweet
    {
        public long Id { get; set; }
        public string Username { get; set; } = "";
        public string Handle { get; set; } = "";
        public string Content { get; set; } = "";

        public override string ToString()
        {
            return Username + ":\n" + Content;
        }
    }
}
