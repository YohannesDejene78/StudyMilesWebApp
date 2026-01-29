import { assignments} from './data.js';
let totalTokens;

let userID = localStorage.getItem('userID');

console.log(assignments)

document.addEventListener('DOMContentLoaded', async () => {
    function update() {
        fetchIncentiveData(userID);    
    }
    async function updateEarnedTokens(userID) {
        try {
            // Fetch all incentives
            const response = await fetch("https://studymileswebapp.onrender.com/incentive");
            if (!response.ok) {
                throw new Error("Failed to fetch incentive data");
            }

            const data = await response.json();

            // Find the incentive for the current user
            const result = data.find(item => item.userID.userID === parseInt(userID));
            if (result) {
                const incentiveID = result.incentivesID;

                // Update the incentive using the incentiveID
                const updateResponse = await fetch(`https://studymileswebapp.onrender.com/incentive/${incentiveID}`, {
                    method: "PUT",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify({
                        earnedTokens: totalTokens // Update the earnedTokens field
                    })
                });

                if (!updateResponse.ok) {
                    throw new Error("Failed to update incentive");
                }

                const updatedIncentive = await updateResponse.json();
                console.log("Updated incentive:", updatedIncentive);
                return updatedIncentive; // Return the updated incentive data
            } else {
                console.log("User not found in incentive data. while updating tokens");
                return null; // Return null if no incentive is found
            }
        } catch (error) {
            console.error("Error updating incentive:", error);
            return null; // Return null in case of an error
        }
    }

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
                const incentiveID = result.incentivesID;
                const incentiveResponse = await fetch(`https://studymileswebapp.onrender.com/incentive/${incentiveID}`);
                if (!incentiveResponse.ok) {
                    throw new Error("Failed to fetch incentive details");
                }

                const incentiveData = await incentiveResponse.json();

                totalTokens = incentiveData.earnedTokens;
                document.getElementById('tokenCount').textContent = totalTokens; // Update the token counter in the UI
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

    function saveProgressToLocalStorage() {
        localStorage.setItem('totalTokens', totalTokens);
    };

    

    function updateCompleted() {
        const mileDivs = document.querySelectorAll('.mile');
        const submit = document.querySelector('.submitTask');
        mileDivs.forEach((mile, index) => {
            mile.addEventListener('click', ()=>{
                const doneIcon = mile.querySelector('.fa-check');
                if(mile.classList.contains('completed')){
                    if (!doneIcon) {
                        const done = document.createElement('i');
                        done.className = 'fa-solid fa-check'; // Add the Font Awesome check icon
                        mile.prepend(done); // Add the icon as the first child
                    }
                    mile.style.border = '2px solid green';
                }
                else{
                    submit.style.display = 'inline';
                }
            })
        })
    }

    

    updateCompleted();

    update();
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

        document.querySelector("#userName").textContent = userData.name;

    } catch (error) {
        console.error("Error fetching user data:", error);
    }


    
    const optionsContainer = document.querySelector('.options');
    const milesContainer = document.querySelector('.miles');

    
 
    const assignmentAmount = assignments.length;
    let assignmentContent = '';
    for (let i = 0; i < assignmentAmount; i++) {
        const assignment = assignments[i];
        const mileDiv = document.createElement('div');
        mileDiv.classList.add('mile');
        const imgDiv = document.createElement('div');
        const p = document.createElement('p');
        p.textContent = assignment.title;
        mileDiv.appendChild(p);


        const img = document.createElement('img');
        img.src = 'img/icons/token.png'
        imgDiv.textContent = `+${assignment.token}`
        imgDiv.appendChild(img);
        mileDiv.appendChild(imgDiv);
        
        const milecont = document.createElement('div');

        milecont.appendChild(mileDiv)

        mileDiv.style.cursor = 'pointer';
        mileDiv.addEventListener('click', () => {
            
            
        });
        

        assignmentContent += milecont.innerHTML;
        updateCompleted();
        
    }
    

    

    

    const peerReviewContent = `
        <div class="mile">
            <p>Peer review 2</p>
            <div class="img">
                +3<img src="img/icons/token.png" alt="">
            </div>
        </div>
        <div class="mile">
            <p>Peer Review 1</p>
            <div class="img">
                +5<img src="img/icons/token.png" alt="">
            </div>
        </div>
        
    `;


    milesContainer.innerHTML = assignmentContent;


    optionsContainer.addEventListener('click', (e) => {
        if (e.target.tagName === 'H3') {
            // Toggle classes for visual indication
            const options = optionsContainer.querySelectorAll('h3');
            options.forEach(item => {
                item.classList.remove('select');
                item.classList.add('unselect');
            });
            e.target.classList.remove('unselect');
            e.target.classList.add('select');

            // Change the content based on the selected option
            if (e.target.textContent.trim() === "Assignments") {
                milesContainer.innerHTML = assignmentContent;
            } else if (e.target.textContent.trim() === "Peer Reviews") {
                milesContainer.innerHTML = peerReviewContent;
            }
        }
    });

    const taskModal = document.getElementById('taskModal');
    const modalTitle = document.getElementById('modalTitle');
    const modalDetails = document.getElementById('modalDetails');
    const modalToken = document.getElementById('modalToken');
    const closeModal = document.querySelector('.closeModal');
    const submitTask = document.querySelector('.submitTask');
    const mileDivs = document.querySelectorAll('.mile'); // Select all mileDiv elements

    const tokenCountElement = document.querySelector('.tokenCount'); // Update token counter
    const tokenMessage = document.querySelector('.tokenMessage'); // Update token message
    const tokenAnimation = document.querySelector('.token-animation'); // Trigger animation
    let currentMileDiv = null; // To track the currently clicked mileDiv

    // Open the modal when a mileDiv is clicked
   // Open the modal when a mileDiv is clicked
    mileDivs.forEach((mileDiv, index) => {
        
        mileDiv.addEventListener('click', () => {
            const assignment = assignments[index]; 
            console.log(assignment);
            modalTitle.textContent = assignment.title;
            modalDetails.textContent = assignment.detailedInstruction;
            taskModal.style.display = 'block'; 
            currentMileDiv = mileDiv; 

            updateCompleted();
            if(mileDiv.classList.contains('completed')){
                submitTask.style.display = 'none';
            }
            submitTask.addEventListener('click', () => {
                if(!(mileDiv.classList.contains('completed'))){
                        taskModal.style.display = 'none';
                        totalTokens += assignment.token;
                

                        updateEarnedTokens(userID);
                        tokenCountElement.textContent = totalTokens;

                        tokenMessage.textContent = `+${assignment.token} Tokens`;

                        tokenAnimation.style.animation = "growAndFade 1.5s ease-in-out forwards";

                        setTimeout(() => {
                            tokenAnimation.style.animation = "none";
                        }, 1500);
                        saveProgressToLocalStorage();
                        mileDiv.style.border = '2px solid green';
                        const p = mileDiv.querySelector('p')
                        const doneIcon = p.querySelector('.fa-check');
                        if (!doneIcon) {
                            const done = document.createElement('i');
                            done.className = 'fa-solid fa-check';
                            p.prepend(done);
                        }
                
                        mileDiv.classList.add('completed');
                }

                
                
            });
            
        });
        
    });
    
    // Close the modal
    closeModal.addEventListener('click', () => {
        taskModal.style.display = 'none'; // Hide the modal
    });

    
    submitTask.addEventListener('click', ()=>{
        updateCompleted();
    })
    

    // Close the modal when clicking outside the content
    taskModal.addEventListener('click', (e) => {
        if (e.target === taskModal) {
            taskModal.style.display = 'none'; // Hide the modal
        }
    });
    const menuToggle = document.getElementById('menuToggle');
    const nav = document.querySelector('.nav');

    menuToggle.addEventListener('click', () => {
        nav.classList.toggle('active'); // Toggle the 'active' class on the nav bar
    });

});