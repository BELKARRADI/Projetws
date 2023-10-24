<!DOCTYPE html>
<?php
include_once './racine.php';
?>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestion des etudiants</title>
    <!-- Ajout du CDN Bootstrap pour styliser la page -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <div class="container">
        <form method="GET" action="controller/addEtudiant.php">
            <fieldset>
                <legend>Ajouter un nouveau étudiant</legend>
                <table class="table">
                    <tr>
                        <td>Nom : </td>
                        <td><input type="text" class="form-control" name="nom" value="" /></td>
                    </tr>
                    <tr>
                        <td>Prénom :</td>
                        <td><input type="text" class="form-control" name="prenom" value="" /></td>
                    </tr>
                    <tr>
                        <td>Ville</td>
                        <td>
                            <select class="form-control" name="ville">
                                <option value="Marrakech">Marrakech</option>
                                <option value="Rabat">Rabat</option>
                                <option value="Agadir">Agadir</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>Sexe </td>
                        <td>
                            M<input type="radio" name="sexe" value="homme" />
                            F<input type="radio" name="sexe" value="femme" />
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td>
                            <input type="submit" class="btn btn-primary" value="Envoyer" />
                            <input type="reset" class="btn btn-secondary" value="Effacer" />
                        </td>
                    </tr>
                </table>
            </fieldset>
        </form>
        <table class="table table-bordered">
            <thead class="thead-dark">
                <tr>
                    <th>ID</th>
                    <th>Nom</th>
                    <th>Prénom</th>
                    <th>Ville</th>
                    <th>Sexe</th>
                    <th>Supprimer</th>
                    <th>Modifier</th>
                </tr>
            </thead>
            <tbody>
                <?php
                include_once RACINE . '/service/EtudiantService.php';
                $es = new EtudiantService();
                foreach ($es->findAll() as $e) {
                    ?>
                    <tr>
                        <td><?php echo $e->getId(); ?></td>
                        <td><?php echo $e->getNom(); ?></td>
                        <td><?php echo $e->getPrenom(); ?></td>
                        <td><?php echo $e->getVille(); ?></td>
                        <td><?php echo $e->getSexe(); ?></td>
                        <td>
                            <a class="btn btn-danger" href="controller/deleteEtudiant.php?id=<?php echo $e->getId(); ?>">Supprimer</a>
                        </td>
                        <td><a class="btn btn-primary" href="updateEtudiant.php">Modifier</a></td>
                    </tr>
                <?php } ?>
            </tbody>
        </table>
    </div>

    <!-- Ajout du CDN jQuery (nécessaire pour certaines fonctionnalités de Bootstrap) -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <!-- Ajout du CDN Popper.js (nécessaire pour certaines fonctionnalités de Bootstrap) -->
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.3/dist/umd/popper.min.js"></script>
    <!-- Ajout du CDN Bootstrap JavaScript -->
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
