open Reg_alloc

let printvar name str =
  match str with
  | Reg(r) -> Printf.printf "%s : Register R%d\n" name r
  | Stack(o) -> Printf.printf "%s : Stack at offset %d\n" name o

let printfvar fname vars =
  Printf.printf "--- Variable de la fonction %s :\n" fname;
  Hashtbl.iter printvar !vars;
  Printf.printf "\n"

let () =
  let argv = Sys.argv in
  let argc = Array.length argv in
  let filename = Printf.sprintf "../mincaml/%s.ml" (if (argc>1) then argv.(1) else "spill") in
  let ast = (Parser.exp Lexer.token (Lexing.from_channel (open_in filename))) in 
  let mem = Reg_alloc.f ast in
  Hashtbl.iter printfvar !(getFRA mem);
  printfvar "main" (getRA mem)