namespace XCloneDesktop
{
    partial class FormPrincipal
    {
        /// <summary>
        /// Variable del diseñador necesaria.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Limpiar los recursos que se estén usando.
        /// </summary>
        /// <param name="disposing">true si los recursos administrados se deben desechar; false en caso contrario.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Código generado por el Diseñador de Windows Forms

        /// <summary>
        /// Método necesario para admitir el Diseñador. No se puede modificar
        /// el contenido de este método con el editor de código.
        /// </summary>
        private void InitializeComponent()
        {
            this.lblUsuario = new System.Windows.Forms.Label();
            this.lstTweets = new System.Windows.Forms.ListBox();
            this.txtPost = new System.Windows.Forms.TextBox();
            this.btnPublicar = new System.Windows.Forms.Button();
            this.btnBorrarTweet = new System.Windows.Forms.Button();
            this.btnBorrarCuenta = new System.Windows.Forms.Button();
            this.btnCerrarSesion = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // lblUsuario
            // 
            this.lblUsuario.AutoSize = true;
            this.lblUsuario.Font = new System.Drawing.Font("Microsoft Sans Serif", 20.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblUsuario.Location = new System.Drawing.Point(12, 9);
            this.lblUsuario.Name = "lblUsuario";
            this.lblUsuario.Size = new System.Drawing.Size(86, 31);
            this.lblUsuario.TabIndex = 0;
            this.lblUsuario.Text = "label1";
            this.lblUsuario.Click += new System.EventHandler(this.label1_Click);
            // 
            // lstTweets
            // 
            this.lstTweets.FormattingEnabled = true;
            this.lstTweets.Location = new System.Drawing.Point(108, 49);
            this.lstTweets.Name = "lstTweets";
            this.lstTweets.Size = new System.Drawing.Size(573, 342);
            this.lstTweets.TabIndex = 1;
            // 
            // txtPost
            // 
            this.txtPost.Location = new System.Drawing.Point(108, 407);
            this.txtPost.Name = "txtPost";
            this.txtPost.Size = new System.Drawing.Size(449, 20);
            this.txtPost.TabIndex = 2;
            // 
            // btnPublicar
            // 
            this.btnPublicar.Location = new System.Drawing.Point(588, 401);
            this.btnPublicar.Name = "btnPublicar";
            this.btnPublicar.Size = new System.Drawing.Size(93, 31);
            this.btnPublicar.TabIndex = 3;
            this.btnPublicar.Text = "Publicar";
            this.btnPublicar.UseVisualStyleBackColor = true;
            this.btnPublicar.Click += new System.EventHandler(this.btnPublicar_Click);
            // 
            // btnBorrarTweet
            // 
            this.btnBorrarTweet.Location = new System.Drawing.Point(702, 63);
            this.btnBorrarTweet.Name = "btnBorrarTweet";
            this.btnBorrarTweet.Size = new System.Drawing.Size(86, 52);
            this.btnBorrarTweet.TabIndex = 4;
            this.btnBorrarTweet.Text = "BorrarTweet";
            this.btnBorrarTweet.UseVisualStyleBackColor = true;
            this.btnBorrarTweet.Click += new System.EventHandler(this.btnBorrarTweet_Click);
            // 
            // btnBorrarCuenta
            // 
            this.btnBorrarCuenta.Location = new System.Drawing.Point(702, 133);
            this.btnBorrarCuenta.Name = "btnBorrarCuenta";
            this.btnBorrarCuenta.Size = new System.Drawing.Size(86, 46);
            this.btnBorrarCuenta.TabIndex = 5;
            this.btnBorrarCuenta.Text = "Borrar Cuenta";
            this.btnBorrarCuenta.UseVisualStyleBackColor = true;
            this.btnBorrarCuenta.Click += new System.EventHandler(this.btnBorrarCuenta_Click);
            // 
            // btnCerrarSesion
            // 
            this.btnCerrarSesion.Location = new System.Drawing.Point(702, 201);
            this.btnCerrarSesion.Name = "btnCerrarSesion";
            this.btnCerrarSesion.Size = new System.Drawing.Size(86, 43);
            this.btnCerrarSesion.TabIndex = 6;
            this.btnCerrarSesion.Text = "Cerrar Sesión";
            this.btnCerrarSesion.UseVisualStyleBackColor = true;
            this.btnCerrarSesion.Click += new System.EventHandler(this.btnCerrarSesion_Click);
            // 
            // FormPrincipal
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(800, 450);
            this.Controls.Add(this.btnCerrarSesion);
            this.Controls.Add(this.btnBorrarCuenta);
            this.Controls.Add(this.btnBorrarTweet);
            this.Controls.Add(this.btnPublicar);
            this.Controls.Add(this.txtPost);
            this.Controls.Add(this.lstTweets);
            this.Controls.Add(this.lblUsuario);
            this.Name = "FormPrincipal";
            this.Text = "Form1";
            this.Load += new System.EventHandler(this.FormPrincipal_Load);
            this.ResumeLayout(false);
            this.PerformLayout();


        }

        #endregion

        private System.Windows.Forms.Label lblUsuario;
        private System.Windows.Forms.ListBox lstTweets;
        private System.Windows.Forms.TextBox txtPost;
        private System.Windows.Forms.Button btnPublicar;
        private System.Windows.Forms.Button btnBorrarTweet;
        private System.Windows.Forms.Button btnBorrarCuenta;
        private System.Windows.Forms.Button btnCerrarSesion;
    }
}

