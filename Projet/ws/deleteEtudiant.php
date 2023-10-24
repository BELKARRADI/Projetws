<?php
include_once '../racine.php';
include_once RACINE . '/service/EtudiantService.php';

if (isset($_GET['id'])) {
    $studentId = $_GET['id'];

    $etudiantService = new EtudiantService();

    $student = $etudiantService->findById($studentId);
    $etudiantService->delete($student);

    echo "Student with ID $studentId has been deleted successfully.";
} else {
    echo "Student ID is missing in the request.";
}