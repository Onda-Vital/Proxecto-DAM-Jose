using System;
using System.Collections.Generic;
using System.Drawing;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace XCloneDesktop
{
    public partial class FormPrincipal : Form
    {
        private ApiService api = new ApiService();
        private User usuarioActual;
        private List<Tweet> tweets = new List<Tweet>();

        public FormPrincipal(User usuario)
        {
            InitializeComponent();
            usuarioActual = usuario;

            lstTweets.DrawMode = DrawMode.OwnerDrawFixed;
            lstTweets.ItemHeight = 40;

            lstTweets.DrawItem += lstTweets_DrawItem;

        }


        private async void FormPrincipal_Load(object sender, EventArgs e)
        {
            lblUsuario.Text = "Benvido, " + usuarioActual.Username;
            await CargarTweets();
        }

        private async Task CargarTweets()
        {
            tweets = await api.GetTweetsAsync();
            lstTweets.DataSource = null;
            lstTweets.DataSource = tweets;
        }

        private async void btnPublicar_Click(object sender, EventArgs e)
        {
            string contenido = txtPost.Text.Trim();

            if (contenido == "")
            {
                MessageBox.Show("Escribe algo antes de publicar");
                return;
            }

            bool ok = await api.CreateTweetAsync(usuarioActual.Username, contenido);

            if (ok)
            {
                txtPost.Clear();
                await CargarTweets();
            }
            else
            {
                MessageBox.Show("Non se puido publicar o tweet");
            }
        }

        private async void btnBorrarTweet_Click(object sender, EventArgs e)
        {
            if (lstTweets.SelectedItem == null)
            {
                MessageBox.Show("Selecciona un tweet");
                return;
            }

            Tweet tweetSeleccionado = (Tweet)lstTweets.SelectedItem;

            if (tweetSeleccionado.Username != usuarioActual.Username)
            {
                MessageBox.Show("Só podes borrar os teus propios tweets");
                return;
            }

            bool ok = await api.DeleteTweetAsync(tweetSeleccionado.Id, usuarioActual.Username);

            if (ok)
            {
                MessageBox.Show("Tweet borrado");
                await CargarTweets();
            }
            else
            {
                MessageBox.Show("Non se puido borrar o tweet");
            }
        }

        private async void btnBorrarCuenta_Click(object sender, EventArgs e)
        {
            DialogResult resultado = MessageBox.Show(
                "Seguro que queres borrar a conta?",
                "Confirmación",
                MessageBoxButtons.YesNo
            );

            if (resultado == DialogResult.Yes)
            {
                bool ok = await api.DeleteUserAsync(usuarioActual.Id);

                if (ok)
                {
                    MessageBox.Show("Conta borrada correctamente");
                    FormLogin login = new FormLogin();
                    login.Show();
                    this.Hide();
                }
                else
                {
                    MessageBox.Show("Non se puido borrar a conta");
                }
            }
        }

        private void btnCerrarSesion_Click(object sender, EventArgs e)
        {
            FormLogin login = new FormLogin();
            login.Show();
            this.Hide();
        }

        private void lstTweets_DrawItem(object sender, DrawItemEventArgs e)
        {
            if (e.Index < 0) return;

            Tweet tweet = (Tweet)lstTweets.Items[e.Index];

            e.DrawBackground();

            string texto = tweet.Username + ":\n" + tweet.Content;

            e.Graphics.DrawString(texto, e.Font, Brushes.Black, e.Bounds);

            e.DrawFocusRectangle();
        }

        private void label1_Click(object sender, EventArgs e)
        {

        }

        private void button1_Click(object sender, EventArgs e)
        {

        }
    }
}
