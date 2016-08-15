package controllers;

import models.Pessoa;
import models.Usuario;
import play.data.FormFactory;
import play.mvc.*;
import views.html.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class Application extends Controller {

    @Inject
    private FormFactory formFactory;
    private List<Pessoa> listaPessoas = new ArrayList<>();
    private List<Usuario> listaUsuarios = new ArrayList<>();

    @Security.Authenticated(Secured.class)
    public Result index() {
        return ok(index.render(listaPessoas));
    }

    public Result login() {
        return ok(login.render());
    }

    public Result logar() {
        Usuario usuario = formFactory.form(Usuario.class).bindFromRequest().get();
        try {
            autenticarUsuario(usuario);
            return redirect(routes.Application.index());
        } catch (Exception e) {
            flash("erro", e.getMessage());
            return redirect(routes.Application.login());
        }
    }

    public Result deslogar() {
        session().clear();
        return redirect(routes.Application.index());
    }

    private void autenticarUsuario(Usuario usuario) throws Exception {
        for (Usuario user: listaUsuarios) {
            if (user.getLogin().equals(usuario.getLogin())) {
                if (user.getSenha().equals(usuario.getSenha())) {
                    session("login", user.getLogin());
                    return;
                }
            }
        }
        throw new Exception("Login ou senha incorretos.");
    }

    public Result cadastro() {
        return ok(cadastro.render());
    }

    public Result cadastrar() {
        Usuario usuario = formFactory.form(Usuario.class).bindFromRequest().get();
        listaUsuarios.add(usuario);
        flash("sucesso", "Cadastrado com sucesso.");
        return redirect(routes.Application.login());
    }

    @Security.Authenticated(Secured.class)
    public Result cadastrarPessoa() {
        Pessoa pessoa = formFactory.form(Pessoa.class).bindFromRequest().get();
        listaPessoas.add(pessoa);
        return redirect(routes.Application.index());
    }

    @Security.Authenticated(Secured.class)
    public Result pessoa(String cpf) {
        return ok(pessoa.render(getPessoaPorCpf(cpf)));
    }

    @Security.Authenticated(Secured.class)
    public Result editarPessoa(String cpf) {
        Pessoa pessoaAntiga = getPessoaPorCpf(cpf);
        Pessoa pessoaNova = formFactory.form(Pessoa.class).bindFromRequest().get();
        pessoaAntiga.setNome(pessoaNova.getNome());
        pessoaAntiga.setCpf(pessoaNova.getCpf());
        return redirect(routes.Application.index());
    }

    @Security.Authenticated(Secured.class)
    public Result excluirPessoa(String cpf) {
        listaPessoas.remove(getPessoaPorCpf(cpf));
        return redirect(routes.Application.index());
    }

    private Pessoa getPessoaPorCpf(String cpf) {
        for (Pessoa p: listaPessoas) {
            if (p.getCpf().equals(cpf)) {
                return p;
            }
        }
        return null;
    }
}