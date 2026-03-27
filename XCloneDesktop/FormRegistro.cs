using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace XCloneDesktop
{
    public partial class FormRegistro : Form
    {
        private ApiService api = new ApiService();

        public FormRegistro()
        {
            InitializeComponent();
        }

        private async void btnRegistrar_Click(object sender, EventArgs e)
        {
            string username = txtUsername.Text.Trim();
            string email = txtEmail.Text.Trim();
            string password = txtPassword.Text.Trim();
            string displayName = txtDisplayName.Text.Trim();

            if (username == "" || email == "" || password == "" || displayName == "")
            {
                MessageBox.Show("Completa todos os campos");
                return;
            }

            bool ok = await api.RegisterAsync(username, email, password, displayName);

            if (ok)
            {
                MessageBox.Show("Usuario rexistrado correctamente");
                FormLogin login = new FormLogin();
                login.Show();
                this.Hide();
            }
            else
            {
                MessageBox.Show("Erro ao rexistrar usuario");
            }
        }

        private void btnVolver_Click(object sender, EventArgs e)
        {
            FormLogin login = new FormLogin();
            login.Show();
            this.Hide();
        }

        private void txtEmail_TextChanged(object sender, EventArgs e)
        {

        }

   
    }
}
