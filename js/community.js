let totalTokens;
let userID = localStorage.getItem('userID');
document.addEventListener("DOMContentLoaded", async () => {


    function sendMessage (){
        const message = input.value.trim();
        if (message === '') return;
        const messageDiv = document.createElement('div');
        messageDiv.classList.add('messages');
        const mpart = document.createElement('div');
        mpart.classList.add('mPart');
        const pfp = document.createElement('img');
        pfp.src = 'img/icon2/profile3.svg';

        const p = document.createElement('p');
        p.classList.add('message');
        p.textContent = message;
        p.style.display = 'flex';
        p.style.justifyContent = 'flex-start';
        mpart.appendChild(pfp);
        mpart.appendChild(p);
        messageDiv.appendChild(mpart);

        

        const buttondiv = document.createElement('div');
        buttondiv.classList.add('answers');
        const ansbutton = document.createElement('button');
        ansbutton.textContent = 'Answer';
        buttondiv.appendChild(ansbutton);
        messageDiv.appendChild(buttondiv);
        messagesCont.appendChild(messageDiv);
        input.value = '';
        
        

        

        messagesCont.scrollTop = messagesCont.scrollHeight;
        updateMessagesFunction();
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

    await fetchIncentiveData(userID);
    const tokens = document.querySelector('.tokenCount');
    tokens.textContent = totalTokens;

    const input = document.querySelector('.ask');
    const send = document.querySelector('.send');
    const messagesCont = document.querySelector('.messagesCont');
    send.addEventListener('click', sendMessage);
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
    function handleEnterKey(event) {
        if (event.key === 'Enter') {
            event.preventDefault(); // Prevent default behavior (e.g., form submission)
            sendMessage();
        }
    }
    input.addEventListener('keydown', handleEnterKey);
    
    messagesCont.scrollTop = messagesCont.scrollHeight;

    function updateMessagesFunction(){
        const messages = document.querySelectorAll('.messages');
        messages.forEach((message, messageIndex) => {
            const buttondiv = message.querySelector('.answers');
            const ansbutton = buttondiv.querySelector('button');
            ansbutton.addEventListener('click', () => {
                messages.forEach((msg) => {
                    msg.style.display = 'none';
                });
                message.style.display = 'block';
                ansbutton.style.display = 'none';
                
                
                const messagesCont = document.querySelector('.messagesCont');
                
                const input = document.querySelector('.ask');
                const send = document.querySelector('.send');
                input.placeholder = 'Reply';
                send.removeEventListener('click', sendMessage);
                input.removeEventListener('keydown', handleEnterKey);
                
                send.addEventListener('click', () => {
                    let likeCount = 0;
                    let dislikeCount = 0;
                    const message = input.value.trim();
                    if (message === '') return;
                    const messageDiv = document.createElement('div');
                    messageDiv.classList.add('messages');
                    const mpart = document.createElement('div');
                    mpart.classList.add('mPart');
                    const pfp = document.createElement('img');
                    pfp.src = 'img/icon2/profile3.svg';
    
                    const p = document.createElement('p');
                    p.classList.add('message');
                    p.textContent = message;
                    p.style.display = 'flex';
                    p.style.justifyContent = 'flex-start';
                    mpart.appendChild(pfp);
                    mpart.appendChild(p);
                    messageDiv.appendChild(mpart);
                    
                    
                    

                    
                    
                    

    
                    const buttondiv = document.createElement('div');
                    buttondiv.classList.add('answers');
                    const likebutton = document.createElement('button');
                    likebutton.innerHTML = `${likeCount} <i class="fa-solid fa-thumbs-up"></i>`;
                    buttondiv.appendChild(likebutton);
                    const dislikebutton = document.createElement('button');
                    dislikebutton.innerHTML = `${dislikeCount} <i class="fa-solid fa-thumbs-down"></i>`;
                    buttondiv.appendChild(dislikebutton);
                    dislikebutton.style.backgroundColor = 'transparent';
                    dislikebutton.style.fontSize = '1.5rem';
                    dislikebutton.classList.add('dislike-like')
                    likebutton.classList.add('dislike-like')
                    
                    likebutton.style.backgroundColor = 'transparent';
                    likebutton.style.fontSize = '1.5rem';
                    let liked = false; // Track whether the like button is toggled
                    let disliked = false; // Track whether the dislike button is toggled
    
                    likebutton.addEventListener('click', () => {
                        if (!liked) {
                            // If not liked, increment the like count
                            likeCount++;
                            likebutton.innerHTML = `${likeCount} <i class="fa-solid fa-thumbs-up"></i>`;
                            likebutton.style.color = 'green'; // Optional: Change color to indicate liked
                            liked = true; // Mark as liked
    
                            // If disliked, reset the dislike state
                            if (disliked) {
                                dislikeCount--;
                                dislikebutton.innerHTML = `${dislikeCount} <i class="fa-solid fa-thumbs-down"></i>`;
                                dislikebutton.style.color = ''; // Reset color
                                disliked = false;
                            }
                        } else {
                            // If already liked, decrement the like count
                            likeCount--;
                            likebutton.innerHTML = `${likeCount} <i class="fa-solid fa-thumbs-up"></i>`;
                            likebutton.style.color = ''; // Reset color
                            liked = false; // Mark as unliked
                        }
                        const answers = message.querySelectorAll('.answers');
                    });
    
                    dislikebutton.addEventListener('click', () => {
                        if (!disliked) {
                            // If not disliked, increment the dislike count
                            dislikeCount++;
                            dislikebutton.innerHTML = `${dislikeCount} <i class="fa-solid fa-thumbs-down"></i>`;
                            dislikebutton.style.color = 'red'; // Optional: Change color to indicate disliked
                            disliked = true; // Mark as disliked
    
                            // If liked, reset the like state
                            if (liked) {
                                likeCount--;
                                likebutton.innerHTML = `${likeCount} <i class="fa-solid fa-thumbs-up"></i>`;
                                likebutton.style.color = ''; // Reset color
                                liked = false;
                            }
                        } else {
                            // If already disliked, decrement the dislike count
                            dislikeCount--;
                            dislikebutton.innerHTML = `${dislikeCount} <i class="fa-solid fa-thumbs-down"></i>`;
                            dislikebutton.style.color = ''; // Reset color
                            disliked = false; // Mark as undisliked
                        }
                    });
    
                    messageDiv.appendChild(buttondiv);
                    messagesCont.appendChild(messageDiv);
                    input.value = '';
                    messagesCont.scrollTop = messagesCont.scrollHeight;
                });
                
                messagesCont.scrollTop = messagesCont.scrollHeight;
            })
        })
    
    }
    updateMessagesFunction();
    
    const menuToggle = document.getElementById('menuToggle');
    const nav = document.querySelector('.nav');

    menuToggle.addEventListener('click', () => {
        nav.classList.toggle('active'); // Toggle the 'active' class on the nav bar
    });
});

