import java.util.*;

public class WorstFit {

    // Class representing a memory block
    static class MemoryBlock {
        int size;
        boolean isFree;
        int initialSize;

        MemoryBlock(int size) {
            this.size = size;
            this.initialSize = size;
            this.isFree = true;
        }
    }

    // Class representing a job
    static class Job {
        String name;
        int size;
        String allocationBlock;  // Added property to store the allocation block

        Job(String name, int size) {
            this.name = name;
            this.size = size;
            this.allocationBlock = "";  // Initially no allocation
        }
    }

    public static void main(String[] args) {
        // Initialize memory blocks
        List<MemoryBlock> memoryBlocks = new ArrayList<>();
        memoryBlocks.add(new MemoryBlock(600)); // Block1
        memoryBlocks.add(new MemoryBlock(300)); // Block2
        memoryBlocks.add(new MemoryBlock(400)); // Block3
        memoryBlocks.add(new MemoryBlock(250)); // Block4
        memoryBlocks.add(new MemoryBlock(600)); // Block5

        // Initialize jobs
        List<Job> jobs = new ArrayList<>();
        jobs.add(new Job("Job X", 120));
        jobs.add(new Job("Job Y", 450));
        jobs.add(new Job("Job Z", 300));
        jobs.add(new Job("Job W", 90));

        // Allocate memory using Worst Fit algorithm
        for (Job job : jobs) {
            int worstBlockIndex = -1;
            int maxBlockSize = -1;

            // Find the largest free block that can fit the job
            for (int i = 0; i < memoryBlocks.size(); i++) {
                MemoryBlock block = memoryBlocks.get(i);
                if (block.isFree && block.size >= job.size && block.size > maxBlockSize) {
                    maxBlockSize = block.size;
                    worstBlockIndex = i;
                }
            }

            // Allocate the job if a suitable block is found
            if (worstBlockIndex != -1) {
                MemoryBlock block = memoryBlocks.get(worstBlockIndex);
                job.allocationBlock = "Block" + (worstBlockIndex + 1);  // Store allocation block
                System.out.println(job.name + " allocated to " + job.allocationBlock + ". Remaining size: " + (block.size - job.size));
                block.size -= job.size;
                if (block.size == 0) {
                    block.isFree = false; // Mark block as no longer free
                }
            } else {
                System.out.println(job.name + " cannot be allocated (insufficient memory).");
            }
        }

        // Display final allocation table
        System.out.println("\nFinal Allocation Table:");
        System.out.println("Job\t\tMemory Requested(K)\tAllocation Block");
        for (Job job : jobs) {
            if (job.allocationBlock.isEmpty()) {
                // If no allocation block was assigned
                System.out.println(job.name + "\t\t" + job.size + "\t\t\tNot Allocated");
            } else {
                System.out.println(job.name + "\t\t" + job.size + "\t\t\t" + job.allocationBlock);
            }
        }

        // Display final memory block status
        System.out.println("\nFinal Memory Block Status Table:");
        System.out.println("Memory Block\tInitial Size(K)\tRemaining Size(K)\tStatus");
        for (int i = 0; i < memoryBlocks.size(); i++) {
            MemoryBlock block = memoryBlocks.get(i);
            String status = block.size < block.initialSize ? "Busy" : "Free";
            System.out.println("Block" + (i + 1) + "\t\t" + block.initialSize + "\t\t" + block.size + "\t\t" + status);
        }
    }
}
