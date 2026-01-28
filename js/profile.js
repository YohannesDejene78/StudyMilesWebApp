let totalTokens;
let incentiveID;
let userID = localStorage.getItem('userID');

document.addEventListener('DOMContentLoaded', async () => {

    async function fetchIncentiveData(userID) {
        try {
            // Fetch all incentives
            const response = await fetch("https://studymileswebapp.onrender.com/incentive");
            if (!response.ok) {
                throw new Error("Failed to fetch incentive data");
            }

            const data = await response.json();
            const result = data.find(item => item.userID.userID === parseInt(userID));
            if (result) {
                incentiveID = result.incentivesID;
                const incentiveResponse = await fetch(`https://studymileswebapp.onrender.com/incentive/${incentiveID}`);
                if (!incentiveResponse.ok) {
                    throw new Error("Failed to fetch incentive details");
                }

                const incentiveData = await incentiveResponse.json();

                totalTokens = incentiveData.earnedTokens;
                document.getElementById('tokenCount').textContent = totalTokens; // Update the token counter in the UI
                document.getElementById('tokenCount2').textContent = totalTokens;
                return incentiveData; // Return the incentive data for further use
            } else {
                console.log("User not found in incentive data. while fetching");
                return null; // Return null if no incentive is found
            }
        } catch (error) {
            console.error("Error fetching incentive data:", error);
            return null; // Return null in case of an error
        }
    }

    
    fetchIncentiveData(userID);
    if (!userID) {
        alert("No user ID found. Please log in again.");
        window.location.href = "login.html";
        return;
    }
    
    try {
        const response = await fetch(`https://studymileswebapp.onrender.com/new_user/${userID}`);
        
        if (!response.ok) {
            throw new Error("Failed to fetch user data");
        }

        const userData = await response.json();

        document.querySelector(".userName").textContent = userData.name;
        document.querySelector(".userName2").textContent = userData.name;
        document.querySelector(".userIdDisplay").textContent = `User ID: ${userData.userID}`;

        fetchIncentiveData(userID).then(() => {
            document.querySelector(".userIncentiveDisplay").textContent = `Incentive ID: ${incentiveID}`;
        });



    } catch (error) {
        console.error("Error fetching user data:", error);
    }
    const menuToggle = document.getElementById('menuToggle');
    const nav = document.querySelector('.nav');

    menuToggle.addEventListener('click', () => {
        nav.classList.toggle('active'); // Toggle the 'active' class on the nav bar
    });
});