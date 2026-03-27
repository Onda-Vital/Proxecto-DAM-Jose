using System;
using System.Windows.Forms;

namespace XCloneDesktop
{
    public partial class FormLogin : Form
    {
        private ApiService api = new ApiService();

        public FormLogin()
        {
            InitializeComponent();
        }

        private async void btnLogin_Click(object sender, EventArgs e)
        {
            string username = txtUsername.Text.Trim();
            string password = txtPassword.Text.Trim();

            if (username == "" || password == "")
            {
                MessageBox.Show("Completa todos os campos");
                return;
            }

            User usuario = await api.LoginAsync(username, password);

            if (usuario != null)
            {
                FormPrincipal principal = new FormPrincipal(usuario);
                principal.Show();
                this.Hide();
            }
            else
            {
                MessageBox.Show("Usuario ou contrasinal incorrectos");
            }
        }

        private void btnIrRegistro_Click(object sender, EventArgs e)
        {
            FormRegistro registro = new FormRegistro();
            registro.Show();
            this.Hide();
        }

        private void label1_Click(object sender, EventArgs e)
        {

        }

      
    }
}