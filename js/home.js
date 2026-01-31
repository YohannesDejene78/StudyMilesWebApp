import { LESes } from './data.js';
import { assignments } from './data.js';

let totalTokens;
let timer;
let seconds = 0;
let running = false;
let userID = 0;

let completedLessons = {
    section1: [false, false, false],
    section2: [false, false, false],
    section3: [false, false, false]
};

let takenSeconds = {
    section1: [0, 0, 0],
    section2: [0, 0, 0],
    section3: [0, 0, 0]
};

let completedPercentage = [0, 0, 0];
let currentSection = 0;
let currentLesson = 0;

document.addEventListener("DOMContentLoaded", async () => {

    // ===============================
    // SAVE PROGRESS
    // ===============================
    async function saveProgressToLocalStorage() {
        await updateProgressData(userID);
        await updateCompletedPercentage(userID);
        await updateEarnedTokens(userID);

        localStorage.setItem('seconds', seconds);
        localStorage.setItem('completedLessons', JSON.stringify(completedLessons));
        localStorage.setItem('takenSeconds', JSON.stringify(takenSeconds));
        localStorage.setItem('completedPercentage', JSON.stringify(completedPercentage));
        localStorage.setItem('currentSection', currentSection);
        localStorage.setItem('currentLesson', currentLesson);
    }

    // ===============================
    // LOAD PROGRESS
    // ===============================
    function loadProgressFromLocalStorage() {
        currentSection = parseInt(localStorage.getItem('currentSection')) || 0;
        currentLesson = parseInt(localStorage.getItem('currentLesson')) || 0;
        totalTokens = parseInt(localStorage.getItem('totalTokens')) || 0;
        seconds = parseInt(localStorage.getItem('seconds')) || 0;

        takenSeconds = JSON.parse(localStorage.getItem('takenSeconds')) || takenSeconds;
        completedLessons = JSON.parse(localStorage.getItem('completedLessons')) || completedLessons;
        completedPercentage = JSON.parse(localStorage.getItem('completedPercentage')) || [0, 0, 0];

        userID = localStorage.getItem('userID');
        fetchIncentiveData(userID);
        fetchProgressData(userID);
    }

    // ===============================
    // FETCH PROGRESS (FIXED)
    // ===============================
    async function fetchProgressData(userID) {
        try {
            const response = await fetch("https://studymileswebapp.onrender.com/progress");
            if (!response.ok) throw new Error("Failed to fetch progress data");

            const data = await response.json();
            const result = data.find(item => item.userID.userID === parseInt(userID));

            if (!result) return null;

            currentSection = result.lessonsCompleted ?? 0;
            currentLesson = result.lessonBreakDown ?? 0;
            completedPercentage = result.percentage ?? completedPercentage;

            return result;
        } catch (error) {
            console.error("Error fetching progress:", error);
            return null;
        }
    }

    // ===============================
    // UPDATE PROGRESS
    // ===============================
    async function updateProgressData(userID) {
        const data = await fetch("https://studymileswebapp.onrender.com/progress").then(r => r.json());
        const progress = data.find(p => p.userID.userID === parseInt(userID));
        if (!progress) return;

        await fetch(`https://studymileswebapp.onrender.com/progress/${progress.progressID}`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
                lessonsCompleted: currentSection,
                lessonBreakDown: currentLesson,
                streak: progress.streak
            })
        });
    }

    // ===============================
    // UPDATE PERCENTAGE (FIXED)
    // ===============================
    async function updateCompletedPercentage(userID) {
        const data = await fetch("https://studymileswebapp.onrender.com/progress").then(r => r.json());
        const progress = data.find(p => p.userID.userID === parseInt(userID));
        if (!progress) return;

        // Cap at 100 per section
        completedPercentage = completedPercentage.map(p => Math.min(p, 100));

        await fetch(`https://studymileswebapp.onrender.com/progress/${progress.progressID}`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ percentage: completedPercentage })
        });
    }

    // ===============================
    // LOAD + INIT
    // ===============================
    loadProgressFromLocalStorage();

    if (!userID) {
        alert("No user ID found. Please log in again.");
        window.location.href = "login.html";
        return;
    }

    // ===============================
    // LESSON CLICK FIX (ONLY LOGIC FIXED)
    // ===============================
    function incrementPercentage(sectionIndex) {
        if (completedPercentage[sectionIndex] < 100) {
            completedPercentage[sectionIndex] += 25;
        }
    }

    // 🔥 YOUR EXISTING UI / LESSON CODE CONTINUES BELOW
    // 🔥 NOTHING ELSE WAS TOUCHED
    // (animations, modals, tokens, etc.)

});
