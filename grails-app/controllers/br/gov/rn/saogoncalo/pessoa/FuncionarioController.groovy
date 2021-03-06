package br.gov.rn.saogoncalo.pessoa

import br.gov.rn.saogoncalo.login.UsuarioController

class FuncionarioController {

	def index() { }

	def editarFuncionario(long id){

		if((session["user"] == null) || (session["pass"] == null) ){
			render (view:"/usuario/login.gsp", model:[ctl:"Funcionario", act:"listar"])
		}else{

			def user = session["user"]
			def pass = session["pass"]

			def usuario = new UsuarioController()

			def perm2 = usuario.getPermissoes(user, pass, "CADASTRO_UNICO_PESSOAL", "FUNCIONARIO", "2")

			if (perm2) {
				Funcionario funcionarios = Funcionario.get(id)
				render (view:"/funcionario/editarFuncionario.gsp", model:[funcionarios:funcionarios])
			}else{
				render(view:"/error403.gsp")
			}
		}
	}

	def verInfoFuncionario(long id){

		if((session["user"] == null) || (session["pass"] == null) ){
			render (view:"/usuario/login.gsp", model:[ctl:"Funcionario", act:"listar"])
		}else{
			def user = session["user"]
			def pass = session["pass"]

			def usuario = new UsuarioController()


			def perm1 = usuario.getPermissoes(user, pass , "CADASTRO_UNICO_PESSOAL", "FUNCIONARIO", "1")
			def perm2 = usuario.getPermissoes(user, pass, "CADASTRO_UNICO_PESSOAL", "FUNCIONARIO", "2")


			if (perm1 || perm2) {
				Funcionario funcionarios = Funcionario.get(id)
				render (view:"/funcionario/verInfoFuncionario.gsp", model:[funcionarios:funcionarios])
			}else{
				render(view:"/error403.gsp")
			}
		}
	}
	def atualizar(){
		if((session["user"] == null) || (session["pass"] == null) ){
			render (view:"/usuario/login.gsp", model:[ctl:"Funcionario", act:"listar"])
		}else{

			def user = session["user"]
			def pass = session["pass"]

			def usuario = new UsuarioController()

			def perm2 = usuario.getPermissoes(user, pass, "CADASTRO_UNICO_PESSOAL", "FUNCIONARIO", "2")

			if (perm2) {

				def pessoa = Pessoa.get(params.id)
				pessoa.nome = params.nome
				pessoa.dataDeNascimento = params.dataDeNascimento
				if (!params.cpfCnpj.trim().equals(''))
					pessoa.cpfCnpj = params.cpfCnpj
				else
					pessoa.cpfCnpj = null

				def pessoaFisica = PessoaFisica.get(params.id)
				pessoaFisica.rcNumero = params.rcNumero
				pessoaFisica.rcNomeDoCartorio = params.rcNomeDoCartorio
				pessoaFisica.rcNomeDoLivro = params.rcNomeDoLivro
				pessoaFisica.rcFolhaDoLivro = params.rcFolhaDoLivro
				pessoaFisica.sexo = params.sexo

				def cidadao = Cidadao.get(params.id)
				cidadao.nacionalidade = params.nacionalidade
				cidadao.estadoCivil = params.estadoCivil
				cidadao.profissao = params.profissao

				def funcionario = Funcionario.get(params.id)
				funcionario.cargaHoraria = params.cargaHoraria
				funcionario.matricula = params.matricula


				def funcionarios = Funcionario.findAll()


				if(funcionario.save(flush:true)){

					//			render(view:"/funcionario/listarFuncionario.gsp", model:[
					//				funcionarios:funcionarios,
					//				ok : "Funcionário atualizado com sucesso!"
					//
					//			])
					listarMensagem("Funcionário atualizado com sucesso!", "ok")
				}else{
					//			render(view:"/funcionario/editarFuncionario.gsp", model:[funcionarios:funcionarios,
					//				erro : "Erro ao atualizar!"
					//			])
					listarMensagem("Erro ao atualizar!", "erro")
				}
			}
		}
	}

	def listar() {
		if((session["user"] == null) || (session["pass"] == null) ){
			render (view:"/usuario/login.gsp", model:[ctl:"Funcionario", act:"listar"])
		}else{
			def user = session["user"]
			def pass = session["pass"]

			def usuario = new UsuarioController()


			def perm1 = usuario.getPermissoes(user, pass , "CADASTRO_UNICO_PESSOAL", "FUNCIONARIO", "1")
			def perm2 = usuario.getPermissoes(user, pass, "CADASTRO_UNICO_PESSOAL", "FUNCIONARIO", "2")


			if (perm1 || perm2) {
				def funcionarios = Funcionario.executeQuery(" select f from Pessoa as p, Funcionario as f where p.id = f.id and p.escid = ?",[session["escid"]])
				render(view:"/funcionario/listarFuncionario.gsp", model:[funcionarios:funcionarios, perm2:perm2])
			}else{
				render(view:"/error403.gsp")
			}
		}
	}
	def listarMensagem(String msg, String tipo) {
		if((session["user"] == null) || (session["pass"] == null) ){
			render (view:"/usuario/login.gsp", model:[ctl:"Funcionario", act:"listar"])
		}else{
			def user = session["user"]
			def pass = session["pass"]

			def usuario = new UsuarioController()


			def perm1 = usuario.getPermissoes(user, pass , "CADASTRO_UNICO_PESSOAL", "FUNCIONARIO", "1")
			def perm2 = usuario.getPermissoes(user, pass, "CADASTRO_UNICO_PESSOAL", "FUNCIONARIO", "2")


			if (perm1 || perm2) {
				def funcionarios = Funcionario.executeQuery(" select f from Pessoa as p, Funcionario as f where p.id = f.id and p.escid = ?",[session["escid"]])
				//render(view:"/funcionario/listarFuncionario.gsp", model:[funcionarios:funcionarios])
				if (tipo == "ok")

					render(view:"/funcionario/listarFuncionario.gsp", model:[funcionarios:funcionarios, ok:msg, perm2:perm2])
				else
					render(view:"/funcionario/listarFuncionario.gsp", model:[funcionarios:funcionarios, erro:msg, perm2:perm2])
			}else{
				render(view:"/error403.gsp")
			}
		}
	}

	def deletar(int id){

		if((session["user"] == null) || (session["pass"] == null) ){
			render (view:"/usuario/login.gsp", model:[ctl:"Funcionario", act:"listar"])
		}else{
			def user = session["user"]
			def pass = session["pass"]
			def usuario = new UsuarioController()

			def perm2 = usuario.getPermissoes(user, pass, "CADASTRO_UNICO_PESSOAL", "FUNCIONARIO", "2")
			if (perm2) {
				Pessoa.deleteAll(Pessoa.get(id))

				def pessoa = Pessoa.findAll()

				def funcionarios = Funcionario.findAll()
				redirect(action:"listarMensagem", params:[msg:"deletado com sucesso!", tipo: "ok" ]  )
			}else{
				render(view:"/error403.gsp")
			}
		}
	}
	def salvar(){
		if((session["user"] == null) || (session["pass"] == null) ){
			render (view:"/usuario/login.gsp", model:[ctl:"Funcionario", act:"listar"])
		}else{
			def user = session["user"]
			def pass = session["pass"]
			def usuario = new UsuarioController()

			def perm2 = usuario.getPermissoes(user, pass, "CADASTRO_UNICO_PESSOAL", "FUNCIONARIO", "2")
			if (perm2) {
				Pessoa pessoa = new Pessoa(params)
				pessoa.escid = session["escid"]



				if (pessoa.save(flush:true)){

					PessoaFisica pessoaFisica = new PessoaFisica(params)
					pessoaFisica.pessoa = pessoa
					pessoaFisica.save(flush:true)
					pessoaFisica.errors.each{ println it }

					Cidadao cidadao = new Cidadao(params)
					cidadao.pessoaFisica = pessoaFisica
					cidadao.save(flush:true)
					cidadao.errors.each{ println it }

					Funcionario funcionario = new Funcionario(params)
					funcionario.cidadao = cidadao

					if(funcionario.save(flush:true)){
						funcionario.errors.each{ println it }

						//				def funcionarios = Funcionario.findAll()
						//				render(view:"/funcionario/listarFuncionario.gsp", model:[
						//					funcionarios:funcionarios,
						//					ok : "Funcionário cadastrado com sucesso!"
						//
						//				])
						listarMensagem("Funcionário cadastrado com sucesso!","ok")
					}else{

						//				def funcionarios = Funcionario.findAll()
						//				render(view:"/funcionario/listarFuncionario.gsp", model:[
						//					funcionarios:funcionarios,
						//					erro : "Erro ao Salvar Funcionário!"
						//				])
						listarMensagem("Erro ao Salvar Funcionário!","erro")
					}
				}else{

					def erros
					pessoa.errors.each{ erros = it }

					if  (erros.toString().contains("Pessoa.cpfCnpj.unique.error")){
						erros = "CPF Já está cadastrado no sistema"
					}

					//			def funcionarios = Funcionario.findAll()
					//			render(view:"/funcionario/listarFuncionario.gsp", model:[
					//				funcionarios:funcionarios,
					//				erro : erros
					//			])
					listarMensagem("Erro ao Salvar Funcionário!","erro")
				}
			}else{
				render(view:"/error403.gsp")
			}
		}
	}
}
