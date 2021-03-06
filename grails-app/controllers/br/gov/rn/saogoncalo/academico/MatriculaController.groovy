package br.gov.rn.saogoncalo.academico


import br.gov.rn.saogoncalo.login.UsuarioController
import br.gov.rn.saogoncalo.pessoa.Aluno
import br.gov.rn.saogoncalo.pessoa.Escola

class MatriculaController {

	def index() {
	}



	def listar(){

		if((session["user"] == null) || (session["pass"] == null) ){
			render (view:"/usuario/login.gsp", model:[ctl:"Matricula", act:"listar"])
		}else{
			def user = session["user"]
			def pass = session["pass"]

			def usuario = new UsuarioController()

			def perm1 = usuario.getPermissoes(user, pass, "EDUCACAO_ACADEMICO", "MATRICULA", "1")
			def perm2 = usuario.getPermissoes(user, pass, "EDUCACAO_ACADEMICO", "MATRICULA", "2")

			if (perm1 || perm2) {

				Calendar ca = Calendar.getInstance()
				int ano = ca.get(Calendar.YEAR)

				def matricula = Matricula.executeQuery(" select m from Matricula as m, Turma as t " +
						"  where t.id = m.turma.id " +
						"  and t.escola.id = ? and t.anoLetivo = ?",[Long.parseLong(session["escid"].toString()), ano])

				def escolas = Escola.get(Long.parseLong(session["escid"].toString()))


				def alunos = Aluno.executeQuery(" select a from Pessoa as p, Aluno as a  " +
						"  where p.id = a.id and p.escid = ? and p.status = 'Ativo' " +
						"    and a.id not in ( select m.aluno.id " +
						"  						 from Matricula as m, Turma as t " +
						" 					    where t.id = m.turma.id " +
						"   				     	and t.anoLetivo >= ? ) " , [session["escid"], ano])


				def series = Serie.findAll()

				println("Escolas - " + escolas)

				render(view:"/matricula/listarMatricula.gsp", model:[matricula:matricula, escolas:escolas, alunos:alunos, series:series, perm2:perm2])

			}else{
				render(view:"/error403.gsp")
			}
		}
	}


	def listarMensagem(String msg, String tipo){

		if((session["user"] == null) || (session["pass"] == null) ){
			render (view:"/usuario/login.gsp", model:[ctl:"Matricula", act:"listar"])
		}else{
			def user = session["user"]
			def pass = session["pass"]

			def usuario = new UsuarioController()

			def perm1 = usuario.getPermissoes(user, pass, "EDUCACAO_ACADEMICO", "MATRICULA", "1")
			def perm2 = usuario.getPermissoes(user, pass, "EDUCACAO_ACADEMICO", "MATRICULA", "2")

			if (perm1 || perm2) {

				Calendar ca = Calendar.getInstance()
				int ano = ca.get(Calendar.YEAR)

				def matricula = Matricula.executeQuery(" select m from Matricula as m, Turma as t " +
						"  where t.id = m.turma.id " +
						"  and t.escola.id = ? and t.anoLetivo = ?",[Long.parseLong(session["escid"].toString()), ano])

				def escolas = Escola.get(Long.parseLong(session["escid"].toString()))


				def alunos = Aluno.executeQuery(" select a from Pessoa as p, Aluno as a  " +
						"  where p.id = a.id and p.escid = ? " +
						"    and a.id not in ( select m.aluno.id " +
						"  						 from Matricula as m, Turma as t " +
						" 					    where t.id = m.turma.id " +
						"   				     	and t.anoLetivo >= ? ) " , [session["escid"], ano])


				def series = Serie.findAll()

				println("Escolas - " + escolas)

				if (tipo == "ok")

					render(view:"/matricula/listarMatricula.gsp", model:[matricula:matricula, escolas:escolas, alunos:alunos, series:series, ok:msg, perm2:perm2])
				else
					render(view:"/matricula/listarMatricula.gsp", model:[matricula:matricula, escolas:escolas, alunos:alunos, series:series, erro:msg, perm2:perm2])
			}else{
				render(view:"/error403.gsp")
			}
		}
	}

	def deletar(int id){

		if((session["user"] == null) || (session["pass"] == null) ){
			render (view:"/usuario/login.gsp", model:[ctl:"Matricula", act:"listar"])
		}else{
			def user = session["user"]
			def pass = session["pass"]

			def usuario = new UsuarioController()

			def perm2 = usuario.getPermissoes(user, pass, "EDUCACAO_ACADEMICO", "MATRICULA", "2")

			if (perm2) {

				Matricula.deleteAll(Matricula.get(id))

				//redirect(action:"listar" )
				//listarMensagem("Matrícula excluída com sucesso ", "ok")
				redirect(action:"listarMensagem", params:[msg:"Deletado com sucesso!", tipo:"ok"])
			}else{
				render(view:"/error403.gsp")
			}
		}
	}
	def editarMatricula(long id){
		if((session["user"] == null) || (session["pass"] == null) ){
			render (view:"/usuario/login.gsp", model:[ctl:"Matricula", act:"listar"])
		}else{
			def user = session["user"]
			def pass = session["pass"]

			def usuario = new UsuarioController()

			def perm2 = usuario.getPermissoes(user, pass, "EDUCACAO_ACADEMICO", "MATRICULA", "2")

			if (perm2) {

				def escolas = Escola.findAll()
				def series = Serie.findAll()
				Matricula matricula = Matricula.get(id)

				def turmas = Turma.findAllBySerieAndEscola(matricula.turma.serie, matricula.turma.escola)


				def aluno = Aluno.get(matricula.aluno.id)
				render (view:"/matricula/editarMatricula.gsp", model:[matriculas:matricula, escolas:escolas, aluno:aluno, series:series, turmas:turmas])
			}else{
				render(view:"/error403.gsp")
			}
		}
	}



	def atualizar(){
		if((session["user"] == null) || (session["pass"] == null) ){
			render (view:"/usuario/login.gsp", model:[ctl:"Matricula", act:"listar"])
		}else{
			def user = session["user"]
			def pass = session["pass"]

			def usuario = new UsuarioController()

			def perm2 = usuario.getPermissoes(user, pass, "EDUCACAO_ACADEMICO", "MATRICULA", "2")

			if (perm2) {
				def matricula = Matricula.get(params.idMatricula)

				def aluno = Aluno.get(params.aluno)
				def turma = Turma.get(params.turma)


				matricula.aluno = aluno
				matricula.turma = turma

				matricula.dataDaMatricula = new Date()
				matricula.matricula = params.matricula


				//def matriculas = Matricula.findAll()
				Calendar ca = Calendar.getInstance()
				int ano = ca.get(Calendar.YEAR)
				/*def matriculas = Matricula.executeQuery(" select m from Matricula as m, Turma as t " +
				 "  where t.id = m.turma.id " +
				 "  and t.escola.id = ? and t.anoLetivo = ?",[ Long.parseLong(session["escid"].toString()), ano ])*/

				if(matricula.save(flush:true)){
					/*
					 render(view:"/matricula/listarMatricula.gsp", model:[
					 matricula:matriculas,
					 ok : "Matriucla atualizada com sucesso!"
					 ])*/


					listarMensagem("Matricula atualizada com sucesso", "ok")
					//redirect(action: "listar", params: [ok: "Teste OK params"])
				}else{
					/*render(view:"/matricula/listarMatricula.gsp", model:[matricula:matriculas,
					 erro : "Erro ao atualizar!"
					 ])*/


					listarMensagem("Erro ao Atualizar", "erro")
				}
			}
		}
	}

	def salvar(){
		if((session["user"] == null) || (session["pass"] == null) ){
			render (view:"/usuario/login.gsp", model:[ctl:"Matricula", act:"listar"])
		}else{
			def user = session["user"]
			def pass = session["pass"]

			def usuario = new UsuarioController()

			def perm2 = usuario.getPermissoes(user, pass, "EDUCACAO_ACADEMICO", "MATRICULA", "2")

			if (perm2) {


				Matricula matriculaM = new Matricula(params)

				def turma = Turma.findById(params.turma)
				def matriculados = Matricula.findAllByTurma(turma)
				Calendar ca = Calendar.getInstance()
				int ano = ca.get(Calendar.YEAR)


				if(matriculados.size() >= turma.vagas){

					listarMensagem("Turma não possui Vagas", "erro")
				}else{


					if(matriculaM.save(flush:true)){


						listarMensagem("Matrícula realizada com sucesso", "ok")

					}else{


						listarMensagem("Erro ao salvar Matrícula", "erro")
					}
				}
			}else{
				render(view:"/error403.gsp")
			}
		}
	}
}


