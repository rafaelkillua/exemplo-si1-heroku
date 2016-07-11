package controllers;

import models.Pessoa;
import play.data.Form;
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

    public Result index() {
        return ok(index.render(listaPessoas));
    }

    public Result cadastrarPessoa() {
        Pessoa pessoa = formFactory.form(Pessoa.class).bindFromRequest().get();
        listaPessoas.add(pessoa);
        return redirect(routes.Application.index());
    }

    public Result pessoa(String cpf) {
        return ok(pessoa.render(getPessoaPorCpf(cpf)));
    }

    public Result editarPessoa(String cpf) {
        Pessoa pessoaAntiga = getPessoaPorCpf(cpf);
        Pessoa pessoaNova = formFactory.form(Pessoa.class).bindFromRequest().get();
        pessoaAntiga.setNome(pessoaNova.getNome());
        pessoaAntiga.setCpf(pessoaNova.getCpf());
        return redirect(routes.Application.index());
    }

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