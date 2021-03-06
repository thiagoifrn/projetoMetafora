package br.gov.rn.saogoncalo.academico
import grails.converters.*
import br.gov.rn.saogoncalo.login.UsuarioController
import br.gov.rn.saogoncalo.pessoa.Escola

class SalaController {

	def index() {
	}

	def editarSala(long id){
		if((session["user"] == null) || (session["pass"] == null) ){
			render (view:"/usuario/login.gsp", model:[ctl:"Sala", act:"listar"])
		}else{
			def user = session["user"]
			def pass = session["pass"]

			def usuario = new UsuarioController()

			def perm2 = usuario.getPermissoes(user, pass, "EDUCACAO_ACADEMICO", "SALA", "2")

			if (perm2) {

				Sala salas = Sala.get(id)
				render (view:"/sala/editarSala.gsp", model:[salas:salas])
			}else{
				render(view:"/error403.gsp")
			}
		}
	}

	def getSalaByEscola(long id) {

		if((session["user"] == null) || (session["pass"] == null) ){
			render (view:"/usuario/login.gsp", model:[ctl:"Sala", act:"listar"])
		}else{
			def user = session["user"]
			def pass = session["pass"]

			def usuario = new UsuarioController()

			def perm2 = usuario.getPermissoes(user, pass, "EDUCACAO_ACADEMICO", "SALA", "2")

			if (perm2) {

				Escola escola = Escola.get(params.id)

				def result = ["id":escola?.sala.id, "sala":escola?.sala.sala]

				render result as JSON
			}
		}
	}

	def atualizar(){

		if((session["user"] == null) || (session["pass"] == null) ){
			render (view:"/usuario/login.gsp", model:[ctl:"Sala", act:"listar"])
		}else{
			def user = session["user"]
			def pass = session["pass"]

			def usuario = new UsuarioController()

			def perm2 = usuario.getPermissoes(user, pass, "EDUCACAO_ACADEMICO", "SALA", "2")

			if (perm2) {


				def salas = Sala.get(params.id)

				salas.sala = params.sala
				salas.vagas = Integer.parseInt(params.vagas)


				if(salas.save(flush:true)){

					//def sala = Sala.findAll()

					//			render(view:"/sala/listarSala.gsp", model:[
					//				sala:sala,
					//				ok : "Sala atualizado com sucesso!"
					//
					//			])
					listarMensagem("Sala atualizada com sucesso", "ok")
				}else{

					//def sala = Sala.findAll()
					//			render(view:"/sala/editarSala.gsp", model:[sala:sala,
					//				erro : "Erro ao atualizar!"
					//			])
					listarMensagem("Erro ao atualizar", "erro")
				}
			}
		}
	}


	def listar (){

		if((session["user"] == null) || (session["pass"] == null) ){
			render (view:"/usuario/login.gsp", model:[ctl:"Sala", act:"listar"])
		}else{
			def user = session["user"]
			def pass = session["pass"]

			def usuario = new UsuarioController()

			def perm1 = usuario.getPermissoes(user, pass, "EDUCACAO_ACADEMICO", "SALA", "1")
			def perm2 = usuario.getPermissoes(user, pass, "EDUCACAO_ACADEMICO", "SALA", "2")

			if (perm1 || perm2) {

				def sala = Sala.executeQuery(" select s from Sala as s " +
						"  where s.escola.id = ?",[Long.parseLong(session["escid"].toString())])

				def escolas = Escola.get(Long.parseLong(session["escid"].toString()))

				render(view:"/sala/listarSala.gsp", model:[sala:sala, escolas:escolas, perm2:perm2])
			}else{
				render(view:"/error403.gsp")
			}
		}
	}

	def listarMensagem (String msg , String tipo ){

		if((session["user"] == null) || (session["pass"] == null) ){
			render (view:"/usuario/login.gsp", model:[ctl:"Sala", act:"listar"])
		}else{
			def user = session["user"]
			def pass = session["pass"]

			def usuario = new UsuarioController()

			def perm1 = usuario.getPermissoes(user, pass, "EDUCACAO_ACADEMICO", "SALA", "1")
			def perm2 = usuario.getPermissoes(user, pass, "EDUCACAO_ACADEMICO", "SALA", "2")

			if (perm1 || perm2) {

				def sala = Sala.executeQuery(" select s from Sala as s " +
						"  where s.escola.id = ?",[Long.parseLong(session["escid"].toString())])

				def escolas = Escola.get(Long.parseLong(session["escid"].toString()))

				//render(view:"/sala/listarSala.gsp", model:[sala:sala, escolas:escolas])
				if (tipo == "ok"){

					render(view:"/sala/listarSala.gsp", model:[sala:sala, escolas:escolas, ok:msg, perm2:perm2])

				}else
					render(view:"/sala/listarSala.gsp", model:[sala:sala, escolas:escolas, erro:msg, perm2:perm2])
			}else{
				render(view:"/error403.gsp")
			}
		}
	}

	def deletar(int id){
		if((session["user"] == null) || (session["pass"] == null) ){
			render (view:"/usuario/login.gsp", model:[ctl:"Sala", act:"listar"])
		}else{
			def user = session["user"]
			def pass = session["pass"]

			def usuario = new UsuarioController()

			def perm2 = usuario.getPermissoes(user, pass, "EDUCACAO_ACADEMICO", "SALA", "2")

			if (perm2) {

				Sala.deleteAll(Sala.get(id))

				//redirect(action:"listar")
				redirect(action:"listarMensagem", params:[msg:"Deletado com sucesso!", tipo:"ok"])
			}else{
				render(view:"/error403.gsp")
			}
		}
	}
	def salvar(){
		if((session["user"] == null) || (session["pass"] == null) ){
			render (view:"/usuario/login.gsp", model:[ctl:"Sala", act:"listar"])
		}else{
			def user = session["user"]
			def pass = session["pass"]

			def usuario = new UsuarioController()

			def perm2 = usuario.getPermissoes(user, pass, "EDUCACAO_ACADEMICO", "SALA", "2")

			if (perm2) {


				Sala salaL = new Sala(params)

				salaL.sala = params.sala
				salaL.vagas = Integer.parseInt(params.vagas)

				Integer escolaId = Integer.parseInt(params.escola)

				Escola escola = Escola.read(escolaId)

				salaL.escola = escola



				if (salaL.save(flush:true)){

					listarMensagem("Sala cadastrada com sucesso", "ok")
				}else{
					//			def sala = Sala.findAll()
					//			render(view:"/sala/listarSala.gsp", model:[
					//				erro : "Erro ao Salvar!"
					//
					//			])
					listarMensagem("Erro ao salvar", "erro")
				}
			}else{
				render(view:"/error403.gsp")
			}
		}
	}
}