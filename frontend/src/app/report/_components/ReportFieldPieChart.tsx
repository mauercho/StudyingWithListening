'use client'

import {
  Chart as ChartJS,
  ChartData,
  ChartOptions,
  PieController,
  ArcElement,
  Title,
  Tooltip,
  Legend,
} from 'chart.js'
import { Pie } from 'react-chartjs-2'
import ChartDataLabels from 'chartjs-plugin-datalabels'
import { Context } from 'chartjs-plugin-datalabels'
import { StudyInfoProps } from '@/app/report/_components/ReportFieldStudyInfo'

ChartJS.register(PieController, ArcElement, Title, Tooltip, Legend)

export default function ReportFieldPieChart({
  speaking,
  writing,
  lectures,
}: StudyInfoProps) {
  const options: ChartOptions<'pie'> = {
    maintainAspectRatio: false,
    responsive: true,
    plugins: {
      legend: {
        display: false,
      },
      tooltip: {
        enabled: false,
      },
      datalabels: {
        font: {
          family: 'Noto Sans KR',
        },
        padding: {
          left: 20,
          right: 20,
          top: 5,
          bottom: 5,
        },
        align: 'end',
        backgroundColor: '#FAFAFA',
        borderColor: function (context: Context) {
          return (context as any).dataset.backgroundColor
        },
        color: function (context: Context) {
          return (context as any).dataset.backgroundColor
        },
        borderRadius: 16,
        borderWidth: 1,
        formatter: (value, context: Context) => {
          if (context.chart.data.labels) {
            const blanklength =
              4 - (context as any).chart.data.labels[context.dataIndex].length
            const customblank = ' '.repeat(blanklength)
            return `${customblank}${context.chart.data.labels[context.dataIndex]}${customblank}`
          }
        },
        display: (context: Context) => {
          return (context as any).dataset.data[context.dataIndex] > 0
        },
      },
    },
  }

  const labels = ['받아쓰기', 'AI회화', '강의']

  const data: ChartData<'pie', number[]> = {
    labels,
    datasets: [
      {
        label: '총합',
        data: [writing, speaking, lectures],
        backgroundColor: ['#2BAA72', '#F0AF1A', '#664CEC'],
      },
    ],
  }

  return (
    <div className="w-full">
      <Pie options={options} data={data} plugins={[ChartDataLabels as any]} />
    </div>
  )
}
